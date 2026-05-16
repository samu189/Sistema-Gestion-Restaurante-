package com.restaurante.ms_notificaciones.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificacionDTO {
    private Long id;
    private String destinatario;
    private String mensaje;
    private String tipo;
    private LocalDateTime fechaEnvio;
}
