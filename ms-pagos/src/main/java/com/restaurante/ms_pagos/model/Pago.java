package com.restaurante.ms_pagos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del pedido es obligatorio para registrar el pago")
    private Long pedidoId;

    @NotNull(message = "El monto del pago es obligatorio")
    @Min(value = 1, message = "El monto a pagar debe ser mayor o igual a 1")
    private Double monto;

    @NotBlank(message = "El método de pago es obligatorio (EFECTIVO, TARJETA, TRANSFERENCIA)")
    private String metodoPago;

    private String estado;     // COMPLETADO, PENDIENTE, FALLIDO
    private LocalDateTime fechaPago;
}