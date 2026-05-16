package com.restaurante.ms_pagos.controller;

import com.restaurante.ms_pagos.model.Pago;
import com.restaurante.ms_pagos.response.ApiResponse;
import com.restaurante.ms_pagos.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pago>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>("Historial de pagos obtenido con éxito", service.listarTodos()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Pago>> crear(@RequestBody Pago pago) {
        return ResponseEntity.ok(new ApiResponse<>("Pago procesado de forma exitosa", service.procesarPago(pago)));
    }
}