package com.restaurante.ms_reservas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del cliente para la reserva es obligatorio")
    private String nombreCliente;

    @NotNull(message = "El número de personas es obligatorio")
    @Min(value = 1, message = "La reserva debe ser al menos para 1 persona")
    private Integer numeroPersonas;

    @NotNull(message = "La fecha y hora de la reserva es obligatoria")
    @FutureOrPresent(message = "La fecha de la reserva debe ser actual o en el futuro")
    private LocalDateTime fechaReserva;

    @NotBlank(message = "La mesa asignada o zona es obligatoria")
    private String mesaAsignada;

    @NotBlank(message = "El estado de la reserva es obligatorio (CONFIRMADA, CANCELADA)")
    private String estado = "CONFIRMADA";
}
