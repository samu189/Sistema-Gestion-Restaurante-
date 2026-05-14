package com.restaurante.ms_inventario.controller;

import com.restaurante.ms_inventario.service.InventarioService;
import com.restaurante.ms_inventario.dto.InventarioDTO;
import com.restaurante.ms_inventario.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    @Autowired
    private InventarioService service;

    @GetMapping
    public ApiResponse listar() {
        return new ApiResponse("Lista de inventario obtenida", service.listarTodo());
    }

    @PostMapping
    public ApiResponse crear(@RequestBody InventarioDTO dto) {
        return new ApiResponse("Item agregado al inventario", service.crear(dto));
    }
}