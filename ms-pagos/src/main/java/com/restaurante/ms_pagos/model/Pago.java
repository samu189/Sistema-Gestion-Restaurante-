package com.restaurante.ms_pagos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
@Schema(description = "Modelo transaccional que representa la liquidación o cobro de un pedido")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la transacción en la base de datos", example = "45")
    private Long id;

    @NotNull(message = "El ID del pedido es obligatorio para registrar el pago")
    @Schema(description = "Referencia del pedido al que corresponde este cobro financiero", example = "1001")
    private Long pedidoId;

    @NotNull(message = "El monto del pago es obligatorio")
    @Min(value = 1, message = "El monto a pagar debe ser mayor o igual a 1")
    @Schema(description = "Valor total monetario cobrado por la orden", example = "35.50")
    private Double monto;

    @NotBlank(message = "El método de pago es obligatorio (EFECTIVO, TARJETA, TRANSFERENCIA)")
    @Schema(description = "Pasarela o forma utilizada por el comensal para liquidar la cuenta", example = "TARJETA")
    private String metodoPago;

    @Schema(description = "Estado de resolución del intento de cobro", example = "COMPLETADO")
    private String estado;     // COMPLETADO, PENDIENTE, FALLIDO

    @Schema(description = "Fecha y hora exacta en la que se confirmó el pago", example = "2026-06-14T21:24:00")
    private LocalDateTime fechaPago;
}