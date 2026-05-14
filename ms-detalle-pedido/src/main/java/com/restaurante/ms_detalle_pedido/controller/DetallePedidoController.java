package com.restaurante.ms_detalle_pedido.controller;

import com.restaurante.ms_detalle_pedido.model.DetallePedido;
import com.restaurante.ms_detalle_pedido.response.ApiResponse;
import com.restaurante.ms_detalle_pedido.service.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/detalle-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DetallePedido>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>("Detalles obtenidos", service.listarTodos()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DetallePedido>> crear(@RequestBody DetallePedido detalle) {
        return ResponseEntity.ok(new ApiResponse<>("Detalle registrado correctamente", service.guardar(detalle)));
    }
}
