package com.restaurante.ms_detalle_pedido.controller;

import com.restaurante.ms_detalle_pedido.model.DetallePedido;
import com.restaurante.ms_detalle_pedido.response.ApiResponse;
import com.restaurante.ms_detalle_pedido.service.DetallePedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/detalle-pedido")
@Slf4j
@Tag(name = "Detalle Pedido Controller", description = "Endpoints para gestionar los platos/ítems individuales dentro de un pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService service;

    @GetMapping
    @Operation(summary = "Listar todos los detalles", description = "Obtiene una lista con el desglose de absolutamente todos los pedidos de la base de datos")
    public ResponseEntity<ApiResponse<List<DetallePedido>>> listar() {
        log.info("Petición HTTP: GET /api/detalle-pedido - Listando el desglose de todos los pedidos.");
        return ResponseEntity.ok(new ApiResponse<>("Detalles obtenidos con éxito", service.listarTodos()));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo ítem/detalle", description = "Registra un producto, cantidad y precio asociado a un ID de pedido principal")
    public ResponseEntity<ApiResponse<DetallePedido>> crear(@Valid @RequestBody DetallePedido detalle) {
        log.info("Petición HTTP: POST /api/detalle-pedido - Registrando ítem para el Pedido ID: {} (Menu ID: {}, Cantidad: {})",
                detalle.getPedidoId(), detalle.getMenuId(), detalle.getCantidad());
        DetallePedido nuevoDetalle = service.guardar(detalle);
        return new ResponseEntity<>(new ApiResponse<>("Detalle registrado correctamente", nuevoDetalle), HttpStatus.CREATED);
    }
}