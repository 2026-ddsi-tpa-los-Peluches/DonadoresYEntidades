package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.Queja;

import java.util.ArrayList;
import java.util.Optional;

public interface QuejaRepository {
  Optional<Queja> findById(String id);
  ArrayList<Queja> findAll();

  Queja save(Queja queja);

  Queja deleteById(String id);
}
