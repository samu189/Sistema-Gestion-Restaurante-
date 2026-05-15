package com.restaurante.ms_cocina.controller;

import com.restaurante.ms_cocina.model.Cocina;
import com.restaurante.ms_cocina.response.ApiResponse;
import com.restaurante.ms_cocina.service.CocinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cocina")
public class CocinaController {

    @Autowired
    private CocinaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cocina>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>("Órdenes de cocina recuperadas", service.listarOrdenes()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cocina>> crear(@RequestBody Cocina cocina) {
        return ResponseEntity.ok(new ApiResponse<>("Orden enviada a cocina con éxito", service.guardarOrden(cocina)));
    }
}