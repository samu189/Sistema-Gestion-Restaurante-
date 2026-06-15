package com.restaurante.ms_reservas.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Modelo operativo que representa una reserva de espacio agendada por un comensal")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único incremental asignado por la base de datos", example = "8")
    private Long id;

    @NotBlank(message = "El nombre del cliente para la reserva es obligatorio")
    @Schema(description = "Nombre completo del comensal que titulariza la reserva", example = "Mariana Silva")
    private String nombreCliente;

    @NotNull(message = "El número de personas es obligatorio")
    @Min(value = 1, message = "La reserva debe ser al menos para 1 persona")
    @Schema(description = "Cantidad de cubiertos o asientos requeridos", example = "4")
    private Integer numeroPersonas;

    @NotNull(message = "La fecha y hora de la reserva es obligatoria")
    @FutureOrPresent(message = "La fecha de la reserva debe ser actual o en el futuro")
    @Schema(description = "Estampa de tiempo planificada para el arribo de los comensales", example = "2026-07-20T20:30:00")
    private LocalDateTime fechaReserva;

    @NotBlank(message = "La mesa asignada o zona es obligatoria")
    @Schema(description = "Ubicación o número físico del recurso asignado preliminarmente", example = "Mesa 12 (VIP)")
    private String mesaAsignada;

    @NotBlank(message = "El estado de la reserva es obligatorio (CONFIRMADA, CANCELADA)")
    @Schema(description = "Ciclo operativo actual de la planificación", example = "CONFIRMADA")
    private String estado = "CONFIRMADA";
}