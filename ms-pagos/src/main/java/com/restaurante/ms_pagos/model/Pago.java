package com.restaurante.ms_pagos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pedidoId;
    private Double monto;
    private String metodoPago; // EFECTIVO, TARJETA, TRANSFERENCIA
    private String estado;     // COMPLETADO, PENDIENTE, FALLIDO
    private LocalDateTime fechaPago;
}