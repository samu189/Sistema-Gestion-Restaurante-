package com.restaurante.ms_notificaciones.controller;

import com.restaurante.ms_notificaciones.model.Notificacion;
import com.restaurante.ms_notificaciones.response.ApiResponse;
import com.restaurante.ms_notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@Slf4j // Logs para la auditoría de alertas del sistema
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Notificacion>>> listar() {
        log.info("Petición HTTP recibida: GET /api/notificaciones - Consultando el historial de envíos.");
        return ResponseEntity.ok(new ApiResponse<>("Historial de notificaciones obtenido con éxito", service.obtenerHistorial()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Notificacion>> enviar(@Valid @RequestBody Notificacion notificacion) {
        log.info("Petición HTTP recibida: POST /api/notificaciones - Enviando alerta de tipo [{}] a: {}",
                notificacion.getTipo(), notificacion.getDestinatario());
        Notificacion nuevaNotificacion = service.enviarNotificacion(notificacion);
        return new ResponseEntity<>(new ApiResponse<>("Notificación enviada con éxito", nuevaNotificacion), HttpStatus.CREATED);
    }
}