package com.restaurante.ms_reservas.controller;

import com.restaurante.ms_reservas.model.Reserva;
import com.restaurante.ms_reservas.response.ApiResponse;
import com.restaurante.ms_reservas.service.ReservaService;
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
@RequestMapping("/api/reservas")
@Slf4j
@Tag(name = "Reservas Controller", description = "Endpoints para la gestión, planificación y control de la agenda del local con soporte HATEOAS")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @GetMapping
    @Operation(summary = "Listar la agenda general (HATEOAS)", description = "Retorna el histórico y las próximas reservas agregando enlaces dinámicos autodescriptivos")
    public ResponseEntity<ApiResponse<CollectionModel<EntityModel<Reserva>>>> listar() {
        log.info("Petición HTTP recibida: GET /api/reservas - Consultando la agenda general de reservas.");
        List<Reserva> lista = service.listarTodas();

        // Envolvemos las reservas con el estándar interactivo HATEOAS
        List<EntityModel<Reserva>> hateoasItems = lista.stream()
                .map(reserva -> EntityModel.of(reserva,
                        linkTo(methodOn(ReservaController.class).listar()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Reserva>> model = CollectionModel.of(hateoasItems,
                linkTo(methodOn(ReservaController.class).listar()).withSelfRel());

        return ResponseEntity.ok(new ApiResponse<>("Lista de reservas obtenida con éxito (HATEOAS)", model));
    }

    @PostMapping
    @Operation(summary = "Agendar una nueva reserva (HATEOAS)", description = "Crea un registro de reserva para un cliente en un horario determinado y devuelve el recurso interactivo")
    public ResponseEntity<ApiResponse<EntityModel<Reserva>>> crear(@Valid @RequestBody Reserva reserva) {
        log.info("Petición HTTP recibida: POST /api/reservas - Creando reserva para '{}' (N° personas: {}, Mesa: '{}')",
                reserva.getNombreCliente(), reserva.getNumeroPersonas(), reserva.getMesaAsignada());
        Reserva nuevaReserva = service.guardar(reserva);

        EntityModel<Reserva> entityModel = EntityModel.of(nuevaReserva,
                linkTo(methodOn(ReservaController.class).listar()).withRel("ver-toda-la-agenda"));

        return new ResponseEntity<>(new ApiResponse<>("Reserva creada con éxito (HATEOAS)", entityModel), HttpStatus.CREATED);
    }
}