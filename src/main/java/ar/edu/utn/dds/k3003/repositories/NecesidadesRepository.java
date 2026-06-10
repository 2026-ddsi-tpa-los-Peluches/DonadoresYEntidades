package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.Donador;
import ar.edu.utn.dds.k3003.model.NecesidadMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface NecesidadesRepository extends JpaRepository<NecesidadMaterial, String> {

}
//Optional<NecesidadMaterial> findById(String id);
//
//ArrayList<NecesidadMaterial> findAll();
//NecesidadMaterial save(NecesidadMaterial necesidad);
//
//NecesidadMaterial deleteById(String id);