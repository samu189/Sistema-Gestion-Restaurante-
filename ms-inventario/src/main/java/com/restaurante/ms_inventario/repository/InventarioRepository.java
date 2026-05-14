package com.restaurante.ms_inventario.repository;

import com.restaurante.ms_inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}
