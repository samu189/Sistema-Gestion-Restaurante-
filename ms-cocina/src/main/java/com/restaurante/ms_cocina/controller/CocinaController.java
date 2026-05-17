package com.restaurante.ms_cocina.controller;

import com.restaurante.ms_cocina.model.Cocina;
import com.restaurante.ms_cocina.response.ApiResponse;
import com.restaurante.ms_cocina.service.CocinaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cocina")
@Slf4j // Logs para la consola de la cocina
public class CocinaController {

    @Autowired
    private CocinaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Cocina>>> listar() {
        log.info("Petición HTTP recibida: GET /api/cocina - Consultando todas las órdenes en cocina.");
        return ResponseEntity.ok(new ApiResponse<>("Órdenes de cocina recuperadas con éxito", service.listarOrdenes()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Cocina>> crear(@Valid @RequestBody Cocina cocina) {
        log.info("Petición HTTP recibida: POST /api/cocina - Nueva comanda para el plato: '{}' x{}", cocina.getNombrePlato(), cocina.getCantidad());
        Cocina nuevaOrden = service.guardarOrden(cocina);
        return new ResponseEntity<>(new ApiResponse<>("Orden enviada a cocina con éxito", nuevaOrden), HttpStatus.CREATED);
    }
}