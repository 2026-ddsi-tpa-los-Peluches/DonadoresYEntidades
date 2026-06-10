package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.Donador;
import ar.edu.utn.dds.k3003.model.EntidadBenefica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface EntidadesRepository  extends JpaRepository<EntidadBenefica, String>  {

}
//Optional<EntidadBenefica> findById(String id);
//
//ArrayList<EntidadBenefica> findAll();
//EntidadBenefica save(EntidadBenefica entidad);
//
//EntidadBenefica deleteById(String id);