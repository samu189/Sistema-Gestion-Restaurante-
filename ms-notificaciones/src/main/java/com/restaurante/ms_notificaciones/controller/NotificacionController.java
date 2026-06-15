package com.restaurante.ms_notificaciones.controller;

import com.restaurante.ms_notificaciones.model.Notificacion;
import com.restaurante.ms_notificaciones.response.ApiResponse;
import com.restaurante.ms_notificaciones.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notificaciones")
@Slf4j
@Tag(name = "Notificaciones Controller", description = "Endpoints para la gestión, despacho y auditoría de alertas del sistema con soporte HATEOAS")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    @GetMapping
    @Operation(summary = "Consultar historial de envíos (HATEOAS)", description = "Retorna el log completo de notificaciones emitidas por el restaurante con enlaces dinámicos")
    public ResponseEntity<ApiResponse<CollectionModel<EntityModel<Notificacion>>>> listar() {
        log.info("Petición HTTP recibida: GET /api/notificaciones - Consultando el historial de envíos.");
        List<Notificacion> lista = service.obtenerHistorial();

        // Envolvemos cada notificación con HATEOAS y su respectivo enlace 'self'
        List<EntityModel<Notificacion>> hateoasItems = lista.stream()
                .map(notificacion -> EntityModel.of(notificacion,
                        linkTo(methodOn(NotificacionController.class).listar()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Notificacion>> model = CollectionModel.of(hateoasItems,
                linkTo(methodOn(NotificacionController.class).listar()).withSelfRel());

        return ResponseEntity.ok(new ApiResponse<>("Historial de notificaciones obtenido con éxito (HATEOAS)", model));
    }

    @PostMapping
    @Operation(summary = "Despachar nueva notificación (HATEOAS)", description = "Registra y envía un mensaje (SMS, Email, Push) retornando el recurso interactivo")
    public ResponseEntity<ApiResponse<EntityModel<Notificacion>>> enviar(@Valid @RequestBody Notificacion notificacion) {
        log.info("Petición HTTP recibida: POST /api/notificaciones - Enviando alerta de tipo [{}] a: {}",
                notificacion.getTipo(), notificacion.getDestinatario());
        Notificacion nuevaNotificacion = service.enviarNotificacion(notificacion);

        EntityModel<Notificacion> entityModel = EntityModel.of(nuevaNotificacion,
                linkTo(methodOn(NotificacionController.class).listar()).withRel("ver-historial-de-notificaciones"));

        return new ResponseEntity<>(new ApiResponse<>("Notificación enviada con éxito (HATEOAS)", entityModel), HttpStatus.CREATED);
    }
}