package com.restaurante.ms_mesas.controller;

import com.restaurante.ms_mesas.model.Mesa;
import com.restaurante.ms_mesas.response.ApiResponse;
import com.restaurante.ms_mesas.service.MesaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@Slf4j // Registros en consola para el control del salón
public class MesaController {

    @Autowired
    private MesaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Mesa>>> listar() {
        log.info("Petición HTTP recibida: GET /api/mesas - Verificando el estado del mapa de mesas del salón.");
        return ResponseEntity.ok(new ApiResponse<>("Estado de las mesas recuperado con éxito", service.listarMesas()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Mesa>> crear(@Valid @RequestBody Mesa mesa) {
        log.info("Petición HTTP recibida: POST /api/mesas - Intentando registrar la Mesa N° {} en la zona '{}'",
                mesa.getNumeroMesa(), mesa.getUbicacion());
        Mesa nuevaMesa = service.guardarMesa(mesa);
        return new ResponseEntity<>(new ApiResponse<>("Mesa registrada con éxito", nuevaMesa), HttpStatus.CREATED);
    }
}