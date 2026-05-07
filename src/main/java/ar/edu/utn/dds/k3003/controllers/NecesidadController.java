package ar.edu.utn.dds.k3003.controllers;

import ar.edu.utn.dds.k3003.Fachada;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EntidadBeneficaDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.NecesidadMaterialDTO;
import ar.edu.utn.dds.k3003.model.NecesidadMaterial;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/necesidades")
public class NecesidadController {

  private Fachada fachada;

  public NecesidadController(Fachada fachada) {
    this.fachada = fachada;
  }

  // Opcion 1 utilizando @RequestMapping
  @RequestMapping(method = RequestMethod.POST)

  public ResponseEntity<NecesidadMaterialDTO> postNecesidad(@RequestBody NecesidadMaterialDTO necesidadMaterialDTO) {
    try{

    NecesidadMaterialDTO necesidadAgregada = fachada.registrarNecesidad(necesidadMaterialDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(necesidadAgregada);
    }catch(Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }



  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<NecesidadMaterialDTO>> getNecesidades(@RequestParam String productoID) {
    try {
      List<NecesidadMaterialDTO> necesidades = fachada.obtenerNecesidadesInsatisfechasDe(productoID);
      return ResponseEntity.status(HttpStatus.OK).body(necesidades);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }



  // Opcion 2 utilizando @GetMapping



  //@GetMapping
  //public ResponseEntity<NecesidadDTO> getDonadorByID(@RequestParam String donadorID) {
  //  return ResponseEntity.status(HttpStatus.OK).body(this.fachada.buscarDonadorPorID(donadorID));
  //}
}
