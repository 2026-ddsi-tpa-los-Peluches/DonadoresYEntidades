package ar.edu.utn.dds.k3003;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.*;
import ar.edu.utn.dds.k3003.catedra.fachadas.FachadaDonadoresYEntidades;
import ar.edu.utn.dds.k3003.componentes.DonacionesClient;
import ar.edu.utn.dds.k3003.componentes.IncentivosClient;
import ar.edu.utn.dds.k3003.componentes.LogisticaClient;
import ar.edu.utn.dds.k3003.exceptions.*;
import ar.edu.utn.dds.k3003.model.Donador;
import ar.edu.utn.dds.k3003.model.NecesidadMaterial;
import ar.edu.utn.dds.k3003.repositories.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.val;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Fachada implements FachadaDonadoresYEntidades {

  // 1. Ahora inyectamos las interfaces JPA
  @Autowired
  private DonadoresRepository donadoresRepository; // Ojo, ahora se llama DonadorRepository

  @Autowired
  private EntidadesRepository entidadesRepository;

  @Autowired
  private NecesidadesRepository necesidadesRepository;

  @Autowired
  private QuejaRepository quejaRepository;

  @Autowired
  private IncentivosClient incentivosClient;

  @Autowired
  private DonacionesClient donacionesClient;

  @Autowired
  private LogisticaClient logisticaClient;

  // Tus métricas personalizadas
  private final Counter quejasRegistradasCounter;
  private final Counter erroresNegocioCounter;


  private DonadoresYEntidadesDataMapper donadoresYEntidadesDataMapper =
          new DonadoresYEntidadesDataMapper();
  // 2. El constructor queda vacío
  @Autowired
  public Fachada(MeterRegistry meterRegistry) {
    this.quejasRegistradasCounter = Counter.builder("quejas.registradas")
            .description("Quejas realizadas")
            .register(meterRegistry);

    this.erroresNegocioCounter = Counter.builder("errores.negocio")
            .description("Errores negocio")
            .register(meterRegistry);

  }



  public DonadorDTO asignarMision(Integer donadorId, String misionActualID) {
    // 1. Buscamos el donador
    var donador = this.donadoresRepository.findById(donadorId)
            .orElseThrow(() -> new NoSuchElementException("Donador no encontrado con ID: " + donadorId));

    // Un donador baneado no puede aceptar misiones que le manda Incentivos
    if (donador.estaBaneado()) {
      throw new IllegalStateException(
          "El donador con ID " + donadorId + " esta baneado y no puede aceptar misiones");
    }

    donador.setMisionActualID(misionActualID);

    var donadorGuardado = this.donadoresRepository.save(donador);
    return this.donadoresYEntidadesDataMapper.toDonadorDTO(donadorGuardado);
  }


  @Override
  public DonadorDTO agregarDonador(DonadorDTO donadorDTO) {

    if (donadorDTO.id() != null) {
      throw new IllegalArgumentException("No debe enviarse ID para crear un donador");
    }

    val donador = donadoresYEntidadesDataMapper.toDonador(donadorDTO);

    val donadorGuardado = this.donadoresRepository.save(donador);

    return donadoresYEntidadesDataMapper.toDonadorDTO(donadorGuardado);
  }



  public DonadorDTO agregarInsignia(Integer idDonador,String insigniaID) {


    if(idDonador == null){
      throw new IllegalArgumentException("No debe enviarse ID para crear un donador");
    }
    var donador = this.donadoresRepository.findById(idDonador)
            .orElseThrow(() -> new NoSuchElementException("Donador no encontrado con ID: " + idDonador));

    donador.agregarInsignia(insigniaID);

    // 3. Guardamos en la base de datos para persistir el cambio
    var donadorGuardado = this.donadoresRepository.save(donador);

    // 4. Retornamos el DTO actualizado usando tu mapper
    return this.donadoresYEntidadesDataMapper.toDonadorDTO(donadorGuardado);
  }

  public DonadorDTO quitarInsignia(Integer idDonador, String insigniaID) {

    if (idDonador == null) {
      throw new IllegalArgumentException("El ID del donador es obligatorio");
    }
    var donador = this.donadoresRepository.findById(idDonador)
            .orElseThrow(() -> new NoSuchElementException("Donador no encontrado con ID: " + idDonador));

    donador.quitarInsignia(insigniaID);

    var donadorGuardado = this.donadoresRepository.save(donador);

    return this.donadoresYEntidadesDataMapper.toDonadorDTO(donadorGuardado);
  }

  @Override
  public DonadorDTO buscarDonadorPorID(Integer donadorID) throws NoSuchElementException {
    val donadorOptional = this.donadoresRepository.findById(donadorID);

    if (donadorOptional.isEmpty()) {
      throw new DonadorNoEncontradoException("No existe un donador con ese ID");
    }
    val donadorFinal = donadorOptional.get();

    return donadoresYEntidadesDataMapper.toDonadorDTO(donadorFinal);
  }

  @Override
  public DonadorDTO modificarEstado(Integer donadorID, EstadoDonadorEnum estado)
      throws NoSuchElementException {
    if (estado == null) {
      throw new RuntimeException("Estado no puede ser NULL");
    }
    val donadorOptional = this.donadoresRepository.findById(donadorID);

    if (donadorOptional.isEmpty()) {
      throw new DonadorNoEncontradoException("No existe un donador con ese ID");
    }

    val donadorFinal = donadorOptional.get();
    donadorFinal.setEstado(estado);

    // this.donadoresRepository.deleteById(donadorID);
    this.donadoresRepository.save(donadorFinal); // antes habia save

    return donadoresYEntidadesDataMapper.toDonadorDTO(donadorFinal);
  }

  public EntidadBeneficaDTO modificarEntidad(Integer id, EntidadBeneficaDTO dto) {
    var entidad = this.entidadesRepository.findById(id)
            .orElseThrow(() -> new EntidadNoEncontradaException("No existe una entidad con el ID: " + id));

    if (dto.razonSocial() != null) {
      entidad.setRazonSocial(dto.razonSocial());
    }
    if (dto.domicilio() != null) {
      entidad.setDomicilio(dto.domicilio());
    }
    if (dto.telefono() != null) {
      entidad.setTelefono(dto.telefono());
    }
    if (dto.correo() != null) {
      entidad.setEmail(dto.correo());
    }

    var entidadGuardada = this.entidadesRepository.save(entidad);
    return donadoresYEntidadesDataMapper.toEntidadBeneficaDTO(entidadGuardada);
  }



  @Override
  public DonadorDTO modificarCategoria(Integer donadorID, String categoria)
      throws NoSuchElementException {
    if (categoria == null) {
      throw new RuntimeException("Categoria no puede ser null");
    }
    val donadorOptional = this.donadoresRepository.findById(donadorID);
    if (donadorOptional.isEmpty()) {
      throw new DonadorNoEncontradoException("No existe un donador con ese ID");
    }
    val donadorFinal = donadorOptional.get();
    donadorFinal.setCategoria(categoria);

    // this.donadoresRepository.deleteById(donadorID); la saque porque ya se encarga en InMemoRepo
    this.donadoresRepository.save(donadorFinal); // antes habia un save

    return donadoresYEntidadesDataMapper.toDonadorDTO(donadorFinal);
  }



  @Override
  public Boolean puedeDonar(Integer donadorID) throws NoSuchElementException {
    // A implementar por el alumno
    if (donadorID == null) throw new RuntimeException("El donadoID no puede ser NULL");
    Donador donador =
        donadoresRepository
            .findById(donadorID)
            .orElseThrow(() -> new RuntimeException("No existe el donador con id " + donadorID));
    return donador.puedeDonar();
  }

  @Override
  public List<QuejaDTO> obtenerQuejasDe(Integer donadorID) throws NoSuchElementException {
    // A implementar por el alumno
    donadoresRepository
        .findById(donadorID)
        .orElseThrow(() -> new RuntimeException("No existe el donador con id" + donadorID));
    return quejaRepository.findAll().stream()
        .filter(queja -> queja.getDonadorId().equals(donadorID.toString()))
        .map(donadoresYEntidadesDataMapper::toQuejaDTO)
        .toList();
  }

  @Override
  public NecesidadMaterialDTO satisfacerNecesidad(Integer necesidadID, Integer cantidad)
      throws NoSuchElementException {
    // A implementar por el alumno
    NecesidadMaterial necesidad =
        this.necesidadesRepository
            .findById(necesidadID)
            .orElseThrow(
                () -> new NecesidadNoEncontradaException("No existe una necesidad con ese ID"));

    necesidad.satisfacer(cantidad);
    this.necesidadesRepository.save(necesidad);

    return null;
  }

  @Override
  public DonadorStatsDTO estadisticasDonador(Integer donadorID) {

    Donador donador = this.donadoresRepository
            .findById(donadorID)
            .orElseThrow(() ->
                    new DonadorNoEncontradoException("No existe el donador"));

    return new DonadorStatsDTO(
            donador.getId(),
            donador.getNombre(),
            donador.getApellido(),
            donador.getEdad(),
            donador.getEstado(),
            donador.getCategoria(),
            donador.getMisionActualID(),
            donador.getInsigniasID()
    );
  }

  @Override
  public EntidadBeneficaDTO agregarEntidad(EntidadBeneficaDTO entidadBeneficaDTO) {
    // A implementar por el alumno
    if (entidadBeneficaDTO.id() != null) {
      throw new IllegalArgumentException("No debe enviarse ID para crear una entidad");
    }

    val entidad = donadoresYEntidadesDataMapper.toEntidadBenefica(entidadBeneficaDTO);
    val entidadGuardada = this.entidadesRepository.save(entidad);

    return donadoresYEntidadesDataMapper.toEntidadBeneficaDTO(entidadGuardada);
    // metodo-fachada-hecho
  }

  @Override
  public EntidadBeneficaDTO buscarEntidadPorID(Integer entidadID) throws NoSuchElementException {
    // A implementar por el alumno
    val entidadOptional = this.entidadesRepository.findById(entidadID);

    if (entidadOptional.isEmpty()) {
      throw new EntidadNoEncontradaException("No existe una entidad con ese ID");
    }
    val entidadFinal = entidadOptional.get();
    return donadoresYEntidadesDataMapper.toEntidadBeneficaDTO(entidadFinal);
    // metodo-fachada-hecho
  }

  @Override
  public NecesidadMaterialDTO registrarNecesidad(NecesidadMaterialDTO necesidadMaterialDTO) {

    if (necesidadMaterialDTO.id() != null) {
      throw new NecesidadYaExistenteException("Ingresar una necesidad sin ID");
    }

    if (necesidadMaterialDTO.entidadID() == null) {
      throw new IllegalArgumentException("El entidadID es obligatorio");
    }

    // Validar producto con el módulo de Donaciones
    String productoId = necesidadMaterialDTO.productoSolicitadoID();
    if (!donacionesClient.existeProducto(productoId)) {
      throw new NoSuchElementException("No existe el producto id");
    }

    // Ver que existe la entidad
    Integer entidadId = Integer.valueOf(necesidadMaterialDTO.entidadID());
    var entidadBenefica = entidadesRepository.findById(entidadId)
            .orElseThrow(() -> new NoSuchElementException("No existe una entidad con ID " + entidadId));

    // Guardo necesidad en la DB (La necesidad se crea sí o sí)
    val necesidad = donadoresYEntidadesDataMapper.toNecesidadMaterial(necesidadMaterialDTO);
    necesidad.setEntidadBenefica(entidadBenefica);
    val necesidadGuardada = this.necesidadesRepository.save(necesidad);

    NecesidadMaterialDTO DTOConID = donadoresYEntidadesDataMapper.toNecesidadMaterialDTO(necesidadGuardada);

    // Consultar a Logística pasándole el DTO
    Integer cantidadAsignada = 0;
    try {
      cantidadAsignada = logisticaClient.asignarProductoAEntidad(DTOConID);
    } catch (NoSuchElementException e) {
      // Si Logística no tiene stock, capturamos el error: la necesidad SÍ se creó correctamente
      cantidadAsignada = 0;
    }

    int asignada = (cantidadAsignada != null) ? cantidadAsignada : 0;

    if (asignada > 0) {
      necesidadGuardada.satisfacer(asignada);
      // Guardamos la actualización en BD si se le asignó stock
      val necesidadActualizada = this.necesidadesRepository.save(necesidadGuardada);
      return donadoresYEntidadesDataMapper.toNecesidadMaterialDTO(necesidadActualizada);
    }

    return DTOConID;
  }


  @Override
  public QuejaDTO agregarQueja(QuejaDTO quejaDTO) throws NoSuchElementException {
    try {
      if (quejaDTO.id() != null) {
        throw new NecesidadYaExistenteException("Ingresar una queja sin ID");
      }


      if (quejaDTO.donadorID() == null) {
        throw new IllegalArgumentException("El donadorID es obligatorio");
      }

      Integer donadorId = Integer.valueOf(quejaDTO.donadorID());

      Optional<Donador> donador = this.donadoresRepository.findById(donadorId);

      if (donador.isEmpty()) {
        throw new NoSuchElementException("No existe un donador con ID " + donadorId);
      }
      val queja = donadoresYEntidadesDataMapper.toQueja(quejaDTO);
      val quejaGuardada = this.quejaRepository.save(queja);

      // --- INCREMENTAR MÉTRICA AQUÍ ---
      this.quejasRegistradasCounter.increment();

      Donador donadorClonado= donador.get();
      donadorClonado.aumentarQueja(1);

      this.donadoresRepository.save(donadorClonado);

      return donadoresYEntidadesDataMapper.toQuejaDTO(quejaGuardada);
    } catch (Exception e) {
      // --- INCREMENTAR MÉTRICA DE ERROR AQUÍ ---
      this.erroresNegocioCounter.increment();
      throw e;
    }
  }

  @Override
  public List<NecesidadMaterialDTO> obtenerNecesidadesInsatisfechasDe(String productoSolicitado) {
    // A implementar por el alumno
    return necesidadesRepository.findAll().stream()
        .filter(
            necesidad -> necesidad.esDelProducto(productoSolicitado) && !necesidad.estaSatisfecha())
        .map(donadoresYEntidadesDataMapper::toNecesidadMaterialDTO)
        .toList();
  }

  public List<DonadorDTO> obtenerDonadores() {
    return this.donadoresRepository.findAll().stream()
        .map(donadoresYEntidadesDataMapper::toDonadorDTO)
        .toList();
  }

  public List<EntidadBeneficaDTO> obtenerEntidades() {
    return this.entidadesRepository.findAll().stream()
        .map(donadoresYEntidadesDataMapper::toEntidadBeneficaDTO)
        .toList();
  }



  public NecesidadMaterialDTO buscarNecesidadPorID(Integer id) {
    NecesidadMaterial necesidad = this.necesidadesRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("No existe una necesidad con ID: " + id));
    return donadoresYEntidadesDataMapper.toNecesidadMaterialDTO(necesidad);
  }

  public void borrarNecesidadPorID(Integer id) {
    if (!this.necesidadesRepository.existsById(id)) {
      throw new NoSuchElementException("No existe una necesidad con ID: " + id);
    }
    this.necesidadesRepository.deleteById(id);
  }

  public NecesidadMaterialDTO editarNecesidad(Integer id, NecesidadMaterialDTO dto) {
    NecesidadMaterial necesidad = this.necesidadesRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("No existe una necesidad con ID: " + id));

    // Solo actualizamos los campos permitidos
    if (dto.nivelDeUrgencia() != null) {
      necesidad.setNivelDeUrgencia(dto.nivelDeUrgencia());
    }
    if (dto.descripcion() != null) {
      necesidad.setDescripcion(dto.descripcion());
    }

    NecesidadMaterial necesidadGuardada = this.necesidadesRepository.save(necesidad);
    return donadoresYEntidadesDataMapper.toNecesidadMaterialDTO(necesidadGuardada);
  }




}



