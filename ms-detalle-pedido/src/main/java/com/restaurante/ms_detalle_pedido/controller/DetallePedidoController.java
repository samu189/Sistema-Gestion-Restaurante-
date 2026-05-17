package com.restaurante.ms_detalle_pedido.controller;

import com.restaurante.ms_detalle_pedido.model.DetallePedido;
import com.restaurante.ms_detalle_pedido.response.ApiResponse;
import com.restaurante.ms_detalle_pedido.service.DetallePedidoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/detalle-pedido")
@Slf4j // Activamos los registros de log en consola
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DetallePedido>>> listar() {
        log.info("Petición HTTP: GET /api/detalle-pedido - Listando el desglose de todos los pedidos.");
        return ResponseEntity.ok(new ApiResponse<>("Detalles obtenidos con éxito", service.listarTodos()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DetallePedido>> crear(@Valid @RequestBody DetallePedido detalle) {
        log.info("Petición HTTP: POST /api/detalle-pedido - Registrando ítem para el Pedido ID: {} (Menu ID: {}, Cantidad: {})",
                detalle.getPedidoId(), detalle.getMenuId(), detalle.getCantidad());
        DetallePedido nuevoDetalle = service.guardar(detalle);
        return new ResponseEntity<>(new ApiResponse<>("Detalle registrado correctamente", nuevoDetalle), HttpStatus.CREATED);
    }
}
