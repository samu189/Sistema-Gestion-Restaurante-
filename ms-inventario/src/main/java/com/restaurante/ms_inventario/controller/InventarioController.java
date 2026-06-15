package com.restaurante.ms_inventario.controller;

import com.restaurante.ms_inventario.model.Inventario;
import com.restaurante.ms_inventario.service.InventarioService;
import com.restaurante.ms_inventario.dto.InventarioDTO;
import com.restaurante.ms_inventario.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventario")
@Slf4j
@Tag(name = "Inventario Controller", description = "Endpoints para el control de insumos en el almacén con soporte HATEOAS")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @GetMapping
    @Operation(summary = "Consultar stock completo (HATEOAS)", description = "Obtiene los insumos de la bodega incluyendo enlaces navegables")
    public ApiResponse listar() {
        log.info("Petición HTTP recibida: GET /api/inventario - Consultando el stock con HATEOAS.");

        // Obtenemos la lista original desde tu servicio
        List<Inventario> listaOriginal = (List<Inventario>) service.listarTodo();

        // Convertimos cada ítem en un EntityModel de HATEOAS y le metemos su link "self" ficticio o real
        List<EntityModel<Inventario>> hateoasItems = listaOriginal.stream()
                .map(item -> EntityModel.of(item,
                        linkTo(methodOn(InventarioController.class).listar()).withSelfRel()))
                .collect(Collectors.toList());

        // Envolvemos todo en una colección HATEOAS y le agregamos un link al listado general
        CollectionModel<EntityModel<Inventario>> collectionModel = CollectionModel.of(hateoasItems,
                linkTo(methodOn(InventarioController.class).listar()).withSelfRel());

        return new ApiResponse("Lista de inventario obtenida con éxito (HATEOAS)", collectionModel);
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo lote", description = "Añade un artículo nuevo y retorna el recurso con sus enlaces HATEOAS")
    public ApiResponse crear(@Valid @RequestBody InventarioDTO dto) {
        log.info("Petición HTTP recibida: POST /api/inventario - Agregando con HATEOAS: '{}'", dto.getItem());

        Inventario nuevoItem = (Inventario) service.crear(dto);

        // Creamos la respuesta interactiva agregándole el link hacia el método listar
        EntityModel<Inventario> entityModel = EntityModel.of(nuevoItem,
                linkTo(methodOn(InventarioController.class).listar()).withRel("ver-todo-el-inventario"));

        return new ApiResponse("Item agregado al inventario con éxito (HATEOAS)", entityModel);
    }
}