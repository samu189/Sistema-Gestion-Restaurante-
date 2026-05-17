package com.restaurante.ms_reservas.controller;

import com.restaurante.ms_reservas.model.Reserva;
import com.restaurante.ms_reservas.response.ApiResponse;
import com.restaurante.ms_reservas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@Slf4j // Auditoría de la agenda de reservas en consola
public class ReservaController {

    @Autowired
    private ReservaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Reserva>>> listar() {
        log.info("Petición HTTP recibida: GET /api/reservas - Consultando la agenda general de reservas.");
        return ResponseEntity.ok(new ApiResponse<>("Lista de reservas obtenida con éxito", service.listarTodas()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Reserva>> crear(@Valid @RequestBody Reserva reserva) {
        log.info("Petición HTTP recibida: POST /api/reservas - Creando reserva para '{}' (N° personas: {}, Mesa: '{}')",
                reserva.getNombreCliente(), reserva.getNumeroPersonas(), reserva.getMesaAsignada());
        Reserva nuevaReserva = service.guardar(reserva);
        return new ResponseEntity<>(new ApiResponse<>("Reserva creada con éxito", nuevaReserva), HttpStatus.CREATED);
    }
}