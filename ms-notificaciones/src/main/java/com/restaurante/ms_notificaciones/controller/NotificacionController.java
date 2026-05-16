package com.restaurante.ms_notificaciones.controller;

import com.restaurante.ms_notificaciones.model.Notificacion;
import com.restaurante.ms_notificaciones.response.ApiResponse;
import com.restaurante.ms_notificaciones.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Notificacion>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>("Historial de notificaciones obtenido", service.obtenerHistorial()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Notificacion>> enviar(@RequestBody Notificacion notificacion) {
        return ResponseEntity.ok(new ApiResponse<>("Notificación enviada con éxito", service.enviarNotificacion(notificacion)));
    }
}