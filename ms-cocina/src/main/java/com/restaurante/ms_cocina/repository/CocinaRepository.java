package com.restaurante.ms_cocina.repository;

import com.restaurante.ms_cocina.model.Cocina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocinaRepository extends JpaRepository<Cocina, Long> {
}
