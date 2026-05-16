package com.restaurante.ms_mesas.controller;

import com.restaurante.ms_mesas.model.Mesa;
import com.restaurante.ms_mesas.response.ApiResponse;
import com.restaurante.ms_mesas.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    @Autowired
    private MesaService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Mesa>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>("Estado de las mesas recuperado", service.listarMesas()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Mesa>> crear(@RequestBody Mesa mesa) {
        return ResponseEntity.ok(new ApiResponse<>("Mesa registrada con éxito", service.guardarMesa(mesa)));
    }
}