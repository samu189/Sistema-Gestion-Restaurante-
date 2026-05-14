package com.restaurante.ms_pedidos.repository;

import com.restaurante.ms_pedidos.model.Pedido; // Si usaste model, asegúrate que apunte ahí
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Aquí podrías agregar métodos personalizados si quisieras, por ejemplo:
    // List<Pedido> findByEstado(String estado);
}