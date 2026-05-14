package com.restaurante.ms_detalle_pedido.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalles_pedidos")
@Data
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;   // Relación con ms-pedidos
    private Long menuId;     // Relación con ms-menu
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal; // cantidad * precioUnitario
}