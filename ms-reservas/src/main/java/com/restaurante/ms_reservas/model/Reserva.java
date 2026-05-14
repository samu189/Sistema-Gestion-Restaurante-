package com.restaurante.ms_reservas.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCliente;
    private Integer numeroPersonas;
    private LocalDateTime fechaReserva;
    private String mesaAsignada;
    private String estado; // Ejemplo: CONFIRMADA, CANCELADA
}
