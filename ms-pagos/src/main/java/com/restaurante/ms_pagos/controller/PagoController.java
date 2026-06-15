package com.restaurante.ms_pagos.controller;

import com.restaurante.ms_pagos.model.Pago;
import com.restaurante.ms_pagos.response.ApiResponse;
import com.restaurante.ms_pagos.service.PagoService;
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
@RequestMapping("/api/pagos")
@Slf4j
@Tag(name = "Pagos Controller", description = "Endpoints para el procesamiento y auditoría financiera de las comandas con soporte HATEOAS")
public class PagoController {

    @Autowired
    private PagoService service;

    @GetMapping
    @Operation(summary = "Consultar el historial de transacciones (HATEOAS)", description = "Obtiene la lista completa de pagos registrados, añadiendo enlaces dinámicos autodescriptivos")
    public ResponseEntity<ApiResponse<CollectionModel<EntityModel<Pago>>>> listar() {
        log.info("Petición HTTP recibida: GET /api/pagos - Consultando historial.");
        List<Pago> pagos = service.listarTodos();

        // Envolvemos los registros financieros en contenedores HATEOAS con su respectivo link self
        List<EntityModel<Pago>> hateoasItems = pagos.stream()
                .map(pago -> EntityModel.of(pago,
                        linkTo(methodOn(PagoController.class).listar()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Pago>> model = CollectionModel.of(hateoasItems,
                linkTo(methodOn(PagoController.class).listar()).withSelfRel());

        return ResponseEntity.ok(new ApiResponse<>("Historial de pagos obtenido con éxito (HATEOAS)", model));
    }

    @PostMapping
    @Operation(summary = "Procesar pago de un pedido (HATEOAS)", description = "Registra una transacción monetaria vinculada a una orden y devuelve el recurso interactivo")
    public ResponseEntity<ApiResponse<EntityModel<Pago>>> crear(@Valid @RequestBody Pago pago) {
        log.info("Petición HTTP recibida: POST /api/pagos - Iniciando procesamiento para el Pedido ID: {}", pago.getPedidoId());
        Pago nuevoPago = service.procesarPago(pago);

        EntityModel<Pago> entityModel = EntityModel.of(nuevoPago,
                linkTo(methodOn(PagoController.class).listar()).withRel("ver-todo-el-historial"));

        return new ResponseEntity<>(new ApiResponse<>("Pago procesado de forma exitosa (HATEOAS)", entityModel), HttpStatus.CREATED);
    }
}