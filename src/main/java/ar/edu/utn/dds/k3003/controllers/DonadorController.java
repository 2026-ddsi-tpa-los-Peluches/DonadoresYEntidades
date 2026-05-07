package ar.edu.utn.dds.k3003.controllers;

import ar.edu.utn.dds.k3003.Fachada;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.DonadorDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EstadoDonadorEnum;
import ar.edu.utn.dds.k3003.exceptions.DonadorNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/donadores")
public class DonadorController {

  private Fachada fachada;

  public DonadorController(Fachada fachada) {
    this.fachada = fachada;
  }

  // Opcion 1 utilizando @RequestMapping
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<DonadorDTO> postDonador(@RequestBody DonadorDTO donadorDTO) {
    try {
      DonadorDTO donadorAgregado = fachada.agregarDonador(donadorDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(donadorAgregado);
    }catch(IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  public ResponseEntity<DonadorDTO> getDonador(@PathVariable String id) {
    try {
      DonadorDTO donadorBuscado = fachada.buscarDonadorPorID(id);
      return ResponseEntity.status(HttpStatus.OK).body(donadorBuscado);
    }catch(DonadorNoEncontradoException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<DonadorDTO>> getDonadores() {
    List<DonadorDTO> donadores = fachada.obtenerDonadores();
    return ResponseEntity.status(HttpStatus.OK).body(donadores);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/{id}/puede-donar")
  public ResponseEntity<Map<String, Boolean>> puedeDonar(@PathVariable String id) {
    return ResponseEntity.status(HttpStatus.OK).body(Map.of("puedeDonar",this.fachada.puedeDonar(id)));
  }

  @RequestMapping(method = RequestMethod.PATCH, value = "/{id}/estado")
  public ResponseEntity<DonadorDTO> cambiarEstado(@PathVariable String id, @RequestBody Map<String, String> request ) {

    if (!request.containsKey("estado") || Arrays.stream(EstadoDonadorEnum.values())
            .noneMatch(e -> e.name().equals(request.get("estado"))))
    {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    return ResponseEntity.status(HttpStatus.OK).body(this.fachada.modificarEstado(id,EstadoDonadorEnum.valueOf(request.get("estado"))));
  }

  // Opcion 2 utilizando @GetMapping



  //@GetMapping
  //public ResponseEntity<DonadorDTO> getDonadorByID(@RequestParam String donadorID) {
  //  return ResponseEntity.status(HttpStatus.OK).body(this.fachada.buscarDonadorPorID(donadorID));
  //}
}
