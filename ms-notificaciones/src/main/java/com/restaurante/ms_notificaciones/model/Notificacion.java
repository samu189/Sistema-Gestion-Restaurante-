package com.restaurante.ms_notificaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El destinatario (correo o teléfono) es obligatorio")
    private String destinatario;

    @NotBlank(message = "El contenido del mensaje es obligatorio")
    private String mensaje;

    @NotBlank(message = "El tipo de notificación es obligatorio (CORREO, SMS, PUSH)")
    private String tipo;

    private LocalDateTime fechaEnvio = LocalDateTime.now();
}
