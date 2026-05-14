package com.restaurante.ms_reservas.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservaDTO {
    private Long id;
    private String nombreCliente;
    private Integer numeroPersonas;
    private LocalDateTime fechaReserva;
    private String mesaAsignada;
    private String estado;
}