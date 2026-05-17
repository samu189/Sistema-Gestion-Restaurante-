package com.restaurante.ms_inventario.controller;

import com.restaurante.ms_inventario.service.InventarioService;
import com.restaurante.ms_inventario.dto.InventarioDTO;
import com.restaurante.ms_inventario.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventario")
@Slf4j // Activamos la consola de logs de auditoría
public class InventarioController {

    @Autowired
    private InventarioService service;

    @GetMapping
    public ApiResponse listar() {
        log.info("Petición HTTP recibida: GET /api/inventario - Consultando el stock de almacén.");
        return new ApiResponse("Lista de inventario obtenida con éxito", service.listarTodo());
    }

    @PostMapping
    public ApiResponse crear(@Valid @RequestBody InventarioDTO dto) {
        log.info("Petición HTTP recibida: POST /api/inventario - Agregando nuevo lote al stock: '{}' (Cantidad: {})",
                dto.getItem(), dto.getCantidad());
        return new ApiResponse("Item agregado al inventario con éxito", service.crear(dto));
    }
}