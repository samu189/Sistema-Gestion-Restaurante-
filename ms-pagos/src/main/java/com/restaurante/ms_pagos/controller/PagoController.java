package com.restaurante.ms_pagos.controller;

import com.restaurante.ms_pagos.model.Pago;
import com.restaurante.ms_pagos.response.ApiResponse;
import com.restaurante.ms_pagos.service.PagoService;
import jakarta.validation.Valid; // Importante para las validaciones de la rúbrica
import lombok.extern.slf4j.Slf4j; // Para los logs profesionales
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Slf4j // Activamos el objeto 'log' para la consola
public class PagoController {

    @Autowired
    private PagoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pago>>> listar() {
        log.info("Petición HTTP recibida: GET /api/pagos - Consultando historial.");
        List<Pago> pagos = service.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>("Historial de pagos obtenido con éxito", pagos));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Pago>> crear(@Valid @RequestBody Pago pago) { // <--- Agregamos @Valid aquí
        log.info("Petición HTTP recibida: POST /api/pagos - Iniciando procesamiento para el Pedido ID: {}", pago.getPedidoId());
        Pago nuevoPago = service.procesarPago(pago);
        return new ResponseEntity<>(new ApiResponse<>("Pago procesado de forma exitosa", nuevoPago), HttpStatus.CREATED);
    }
}