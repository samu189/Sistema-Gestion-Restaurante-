package com.restaurante.ms_reservas.controller;

import com.restaurante.ms_reservas.model.Reserva;
import com.restaurante.ms_reservas.response.ApiResponse;
import com.restaurante.ms_reservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Reserva>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>("Lista de reservas obtenida", service.listarTodas()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Reserva>> crear(@RequestBody Reserva reserva) {
        return ResponseEntity.ok(new ApiResponse<>("Reserva creada con éxito", service.guardar(reserva)));
    }
}