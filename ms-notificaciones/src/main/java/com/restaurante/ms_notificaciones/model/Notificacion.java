package com.restaurante.ms_notificaciones.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinatario; // Correo o teléfono del cliente
    private String mensaje;      // Ej: "Tu mesa VIP 1 ya está lista"
    private String tipo;         // CORREO, SMS, PUSH
    private LocalDateTime fechaEnvio;
}
