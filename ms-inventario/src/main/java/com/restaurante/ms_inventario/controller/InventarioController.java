package com.restaurante.ms_inventario.controller;

import com.restaurante.ms_inventario.service.InventarioService;
import com.restaurante.ms_inventario.dto.InventarioDTO;
import com.restaurante.ms_inventario.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventario")
@Slf4j
@Tag(name = "Inventario Controller", description = "Endpoints para el control y auditoría de insumos en el almacén")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @GetMapping
    @Operation(summary = "Consultar stock completo", description = "Obtiene una lista detallada con todos los insumos, cantidades y unidades registrados en la bodega")
    public ApiResponse listar() {
        log.info("Petición HTTP recibida: GET /api/inventario - Consultando el stock de almacén.");
        return new ApiResponse("Lista de inventario obtenida con éxito", service.listarTodo());
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo lote o insumo", description = "Añade un artículo nuevo con su respectiva cantidad inicial y unidad de medida al almacén")
    public ApiResponse crear(@Valid @RequestBody InventarioDTO dto) {
        log.info("Petición HTTP recibida: POST /api/inventario - Agregando nuevo lote al stock: '{}' (Cantidad: {})",
                dto.getItem(), dto.getCantidad());
        return new ApiResponse("Item agregado al inventario con éxito", service.crear(dto));
    }
}