package com.restaurante.ms_cocina.controller;

import com.restaurante.ms_cocina.model.Cocina;
import com.restaurante.ms_cocina.service.CocinaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// IMPORTACIONES ESTÁTICAS PARA HATEOAS Y ANOTACIONES DE SWAGGER
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cocina")
@Slf4j
@Tag(name = "Controlador de Cocina", description = "Endpoints para gestionar las comandas y órdenes de la cocina")
public class CocinaController {

    @Autowired
    private CocinaService service;

    @GetMapping
    @Operation(summary = "Listar órdenes", description = "Obtiene todas las órdenes registradas actualmente en la cocina")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de órdenes recuperada con éxito")
    public ResponseEntity<com.restaurante.ms_cocina.response.ApiResponse<List<Cocina>>> listar() {
        log.info("Petición HTTP recibida: GET /api/cocina - Consultando todas las órdenes en cocina.");
        List<Cocina> lista = service.listarOrdenes();

        // Agregar enlaces HATEOAS a cada elemento de la lista (Opcional pero hiper profesional)
        for (Cocina c : lista) {
            if (!c.hasLinks()) {
                c.add(linkTo(methodOn(CocinaController.class).listar()).withSelfRel());
            }
        }

        return ResponseEntity.ok(new com.restaurante.ms_cocina.response.ApiResponse<>("Órdenes de cocina recuperadas con éxito", lista));
    }

    @PostMapping
    @Operation(summary = "Crear nueva orden", description = "Envía una nueva comanda de plato a la cocina y genera sus hipervínculos HATEOAS")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = { // <-- Le agregamos el prefijo largo aquí
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Orden creada y enviada a cocina con éxito"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<com.restaurante.ms_cocina.response.ApiResponse<Cocina>> crear(@Valid @RequestBody Cocina cocina) {
        log.info("Petición HTTP recibida: POST /api/cocina - Nueva comanda para el plato: '{}' x{}", cocina.getNombrePlato(), cocina.getCantidad());

        // 1. Guardamos la orden normalmente en la BBDD
        Cocina nuevaOrden = service.guardarOrden(cocina);

        // 2. Aplicamos HATEOAS: Le pegamos el link a sí mismo y el link para ver todas las órdenes
        nuevaOrden.add(linkTo(methodOn(CocinaController.class).listar()).withRel("ver-todas-las-ordenes"));

        // 3. Retornamos la respuesta con la estructura que ya usabas
        return new ResponseEntity<>(new com.restaurante.ms_cocina.response.ApiResponse<>("Orden enviada a cocina con éxito", nuevaOrden), HttpStatus.CREATED);
    }
}