package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.DonadorStats;

import java.util.ArrayList;
import java.util.Optional;

public interface DonadorStatsRepository {
  Optional<DonadorStats> findById(String id);

  ArrayList<DonadorStats> findAll();
  DonadorStats save(DonadorStats donadorStats);

  DonadorStats deleteById(String id);
}
