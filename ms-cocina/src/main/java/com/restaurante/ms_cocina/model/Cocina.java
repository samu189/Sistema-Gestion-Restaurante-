package com.restaurante.ms_cocina.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ordenes_cocina")
@Data
public class Cocina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long detallePedidoId; // Referencia al detalle del pedido
    private String nombrePlato;
    private Integer cantidad;
    private String notas;         // Ej: "Sin sal", "Bien cocido"
    private String estado;        // PENDIENTE, EN_PREPARACION, LISTO
}