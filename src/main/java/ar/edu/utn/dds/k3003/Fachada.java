package ar.edu.utn.dds.k3003;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.*;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.InsigniaDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.MisionDTO;
import ar.edu.utn.dds.k3003.catedra.fachadas.FachadaDonadoresYEntidades;
import ar.edu.utn.dds.k3003.catedra.fachadas.FachadaIncentivos;
import ar.edu.utn.dds.k3003.exceptions.*;
import ar.edu.utn.dds.k3003.model.Donador;
import ar.edu.utn.dds.k3003.model.NecesidadMaterial;
import ar.edu.utn.dds.k3003.repositories.*;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class Fachada implements FachadaDonadoresYEntidades {

    private DonadoresRepository donadoresRepository;
    private EntidadesRepository entidadesRepository;
    private NecesidadesRepository necesidadesRepository;
    private QuejaRepository quejaRepository;
    private FachadaIncentivos fachadaIncentivos;
    private DonadoresYEntidadesDataMapper donadoresYEntidadesDataMapper =
            new DonadoresYEntidadesDataMapper();

    public Fachada() {
    /*
    Para que se ejecuten correctamente los tests, se necesita tener un constructor vacio
    Es decir, que no reciba parametros.
    Si necesitan un constructor con parametros
    Java permite tener varios constructores conviviendo sin conflictos.
    */

        this.donadoresRepository = new InMemoryDonadoresRepo();
        this.entidadesRepository = new InMemoryEntidadesRepo();
        this.necesidadesRepository = new InMemoryNecesidadesRepo();
        this.quejaRepository = new InMemoryQuejaRepo();
    }
    //borrar constructor?


    @Override
    public DonadorDTO agregarDonador(DonadorDTO donadorDTO) {
        if (this.donadoresRepository.findById(donadorDTO.id()).isPresent()) {
            throw new DonadorYaExistenteException("Ya existe un donador con ese ID");
        }
        val donador = donadoresYEntidadesDataMapper.toDonador(donadorDTO);

        val donadorGuardado = this.donadoresRepository.save(donador);

        return donadoresYEntidadesDataMapper.toDonadorDTO(donadorGuardado);
    }

    @Override
    public DonadorDTO buscarDonadorPorID(String donadorID) throws NoSuchElementException {
        val donadorOptional = this.donadoresRepository.findById(donadorID);

        if (donadorOptional.isEmpty()) {
            throw new DonadorNoEncontradoException("No existe un donador con ese ID");
        }
        val donadorFinal = donadorOptional.get();

        return donadoresYEntidadesDataMapper.toDonadorDTO(donadorFinal);
    }

    @Override
    public DonadorDTO modificarEstado(String donadorID, EstadoDonadorEnum estado)
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

        this.donadoresRepository.deleteById(donadorID);
        this.donadoresRepository.save(donadorFinal);

        return donadoresYEntidadesDataMapper.toDonadorDTO(donadorFinal);
    }

    @Override
    public DonadorDTO modifcarCategoria(String donadorID, String categoria)
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

        this.donadoresRepository.deleteById(donadorID);
        this.donadoresRepository.save(donadorFinal);

        return donadoresYEntidadesDataMapper.toDonadorDTO(donadorFinal);
    }

    @Override
    public void setFachadaIncentivos(FachadaIncentivos fachadaIncentivos) {
        this.fachadaIncentivos = fachadaIncentivos;
    }

    @Override
    public Boolean puedeDonar(String donadorID) throws NoSuchElementException {
        // A implementar por el alumno
        if (donadorID == null) throw new RuntimeException("El donadoID no puede ser NULL");
        Donador donador = donadoresRepository
                .findById(donadorID)
                .orElseThrow(() -> new RuntimeException("No existe el donador con id " + donadorID));
        return donador.puedeDonar();
    }

    @Override
    public List<QuejaDTO> obtenerQuejasDe(String donadorID) throws NoSuchElementException {
        // A implementar por el alumno
        Donador donador = donadoresRepository
                .findById(donadorID)
                .orElseThrow(() -> new RuntimeException("No existe el donador con id" + donadorID));
        return quejaRepository.findAll().stream()
                .filter(queja -> queja.getDonadorId().equals(donadorID))
                .map(donadoresYEntidadesDataMapper::toQuejaDTO)
                .toList();

    }

    @Override
    public NecesidadMaterialDTO satisfacerNecesidad(String necesidadID, Integer cantidad)
            throws NoSuchElementException {
        // A implementar por el alumno
        NecesidadMaterial necesidad = this.necesidadesRepository
                .findById(necesidadID)
                .orElseThrow(() -> new NecesidadNoEncontradaException("No existe una necesidad con ese ID"));

        necesidad.satisfacer(cantidad);
        return null;
    }

    @Override
    public DonadorStatsDTO estadisticasDonador(String donadorID) {
        DonadorDTO donadorDTO = this.buscarDonadorPorID(donadorID);
        List<InsigniaDTO> insigniasDTO = this.fachadaIncentivos.getInsigniasDeDonador(donadorID);
        MisionDTO misionDTO = this.fachadaIncentivos.getMisionEnCursoDeDonador(donadorID);

        return new DonadorStatsDTO(
                donadorID,
                donadorDTO.nombre(),
                donadorDTO.apellido(),
                donadorDTO.edad(),
                donadorDTO.estado(),
                donadorDTO.categoria(),
                misionDTO.id(),
                insigniasDTO.stream().map(InsigniaDTO::id).toList()
        );


    }

    @Override
    public EntidadBeneficaDTO agregarEntidad(EntidadBeneficaDTO entidadBeneficaDTO) {
        // A implementar por el alumno
        if (this.entidadesRepository.findById(entidadBeneficaDTO.id()).isPresent()) {
            throw new EntidadYaExistenteException("Ya existe una entidad con esa ID");
        }
        val entidad = donadoresYEntidadesDataMapper.toEntidadBenefica(entidadBeneficaDTO);
        val entidadGuardada = this.entidadesRepository.save(entidad);

        return donadoresYEntidadesDataMapper.toEntidadBeneficaDTO(entidadGuardada);
        // metodo-fachada-hecho
    }

    @Override
    public EntidadBeneficaDTO buscarEntidadPorID(String entidadID) throws NoSuchElementException {
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
        // A implementar por el alumno
    /*
    EntidadBenefica entidad = entidadesRepository
            .findById(necesidadMaterialDTO.entidadID())
            .orElseThrow(() -> new RuntimeException
                    ("No existe la entidad con id " + necesidadMaterialDTO.entidadID()));

     */
        if (this.necesidadesRepository.findById(necesidadMaterialDTO.id()).isPresent()) {
            throw new NecesidadYaExistenteException("Ya existe una necesidad con ese ID");
        }
        val necesidad = donadoresYEntidadesDataMapper.toNecesidadMaterial(necesidadMaterialDTO);

        val necesidadGuardada = this.necesidadesRepository.save(necesidad);


        return donadoresYEntidadesDataMapper.toNecesidadMaterialDTO(necesidadGuardada);
    }

    @Override
    public QuejaDTO agregarQueja(QuejaDTO quejaDTO) throws NoSuchElementException {
        // A implementar por el alumno
    /*
    Donador donador = donadoresRepository
            .findById(quejaDTO.donadorID())
            .orElseThrow( () -> new RuntimeException("No existe el donador con id "+ quejaDTO.donadorID()));
    */
        if (this.quejaRepository.findById(quejaDTO.id()).isPresent()) {
            throw new QuejaYaExistenteException("Ya existe una queja con ese ID");
        }
        val queja = donadoresYEntidadesDataMapper.toQueja(quejaDTO);
        val quejaGuardada = this.quejaRepository.save(queja);

        return donadoresYEntidadesDataMapper.toQuejaDTO(quejaGuardada);
    }

    @Override
    public List<NecesidadMaterialDTO> obtenerNecesidadesInsatisfechasDe(String productoSolicitado) {
        // A implementar por el alumno
        return necesidadesRepository.findAll().stream()
                .filter(necesidad -> necesidad.esDelProducto(productoSolicitado))
                .map(donadoresYEntidadesDataMapper::toNecesidadMaterialDTO)
                .toList();
    }

    public List<DonadorDTO> obtenerDonadores() {
        return this.donadoresRepository
                .findAll()
                .stream()
                .map(donadoresYEntidadesDataMapper::toDonadorDTO)
                .toList();

    }

    public List<EntidadBeneficaDTO> obtenerEntidades() {
        return this.entidadesRepository
                .findAll()
                .stream()
                .map(donadoresYEntidadesDataMapper::toEntidadBeneficaDTO)
                .toList();
    }
}
