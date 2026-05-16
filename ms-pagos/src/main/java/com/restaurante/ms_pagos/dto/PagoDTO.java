package com.restaurante.ms_pagos.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PagoDTO {
    private Long id;
    private Long pedidoId;
    private Double monto;
    private String metodoPago;
    private String estado;
    private LocalDateTime fechaPago;
}