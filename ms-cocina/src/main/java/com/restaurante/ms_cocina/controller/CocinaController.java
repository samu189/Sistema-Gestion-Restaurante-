package com.restaurante.ms_cocina.controller;

import com.restaurante.ms_cocina.model.Cocina;
import com.restaurante.ms_cocina.service.CocinaService;
import com.restaurante.ms_cocina.response.ApiResponse;
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
@RequestMapping("/api/cocina")
@Slf4j
@Tag(name = "Cocina Controller", description = "Gestión de órdenes en tiempo real con soporte HATEOAS")
public class CocinaController {

    @Autowired
    private CocinaService service;

    @GetMapping
    @Operation(summary = "Listar órdenes de cocina (HATEOAS)", description = "Obtiene los platos en preparación añadiendo links de navegación")
    public ResponseEntity<ApiResponse<CollectionModel<EntityModel<Cocina>>>> listar() {
        log.info("Petición HTTP: GET /api/cocina - Listando órdenes con HATEOAS.");
        List<Cocina> lista = service.listarOrdenes();

        // Especificamos el método genérico con <CocinaController> para romper el error visual de IntelliJ
        List<EntityModel<Cocina>> hateoasItems = lista.stream()
                .map(orden -> EntityModel.of(orden,
                        linkTo(methodOn(CocinaController.class).listar()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Cocina>> model = CollectionModel.of(hateoasItems,
                linkTo(methodOn(CocinaController.class).listar()).withSelfRel());

        return ResponseEntity.ok(new ApiResponse<>("Órdenes obtenidas con éxito (HATEOAS)", model));
    }

    @PostMapping
    @Operation(summary = "Enviar orden a cocina (HATEOAS)", description = "Envía una nueva comanda y retorna el recurso con sus enlaces")
    public ResponseEntity<ApiResponse<EntityModel<Cocina>>> crear(@Valid @RequestBody Cocina cocina) {
        log.info("Petición HTTP: POST /api/cocina - Creando orden con HATEOAS.");
        Cocina nuevaOrden = service.guardarOrden(cocina);

        EntityModel<Cocina> entityModel = EntityModel.of(nuevaOrden,
                linkTo(methodOn(CocinaController.class).listar()).withRel("ver-todas-las-ordenes"));

        return new ResponseEntity<>(new ApiResponse<>("Orden registrada en cocina (HATEOAS)", entityModel), HttpStatus.CREATED);
    }
}