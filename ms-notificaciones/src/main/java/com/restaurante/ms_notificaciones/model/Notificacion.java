package com.restaurante.ms_notificaciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@Schema(description = "Modelo operativo que representa una alerta o mensaje despachado por el sistema")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autogenerado por la persistencia", example = "102")
    private Long id;

    @NotBlank(message = "El destinatario (correo o teléfono) es obligatorio")
    @Schema(description = "Dirección de destino física o digital de la alerta", example = "cliente_vip@email.com")
    private String destinatario;

    @NotBlank(message = "El contenido del mensaje es obligatorio")
    @Schema(description = "Cuerpo informativo o texto de la notificación despachada", example = "Su pedido #45 ha salido de la cocina y va en camino.")
    private String mensaje;

    @NotBlank(message = "El tipo de notificación es obligatorio (CORREO, SMS, PUSH)")
    @Schema(description = "Canal de comunicación utilizado para el envío", example = "CORREO")
    private String tipo;

    @Schema(description = "Fecha y hora exacta en la que se generó y guardó la alerta", example = "2026-06-14T21:15:00")
    private LocalDateTime fechaEnvio = LocalDateTime.now();
}