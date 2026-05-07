package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.NecesidadMaterial;

import java.util.ArrayList;
import java.util.Optional;

public interface NecesidadesRepository {
  Optional<NecesidadMaterial> findById(String id);

  ArrayList<NecesidadMaterial> findAll();
  NecesidadMaterial save(NecesidadMaterial necesidad);

  NecesidadMaterial deleteById(String id);
}
