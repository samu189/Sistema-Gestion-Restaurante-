package com.restaurante.ms_detalle_pedido.controller;

import com.restaurante.ms_detalle_pedido.model.DetallePedido;
import com.restaurante.ms_detalle_pedido.response.ApiResponse;
import com.restaurante.ms_detalle_pedido.service.DetallePedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detalle-pedido")
@Slf4j
@Tag(name = "Detalle Pedido Controller", description = "Gestión del desglose de ítems por pedido con soporte HATEOAS")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService service;

    @GetMapping
    @Operation(summary = "Listar detalles (HATEOAS)", description = "Obtiene los desgloses agregando enlaces dinámicos")
    public ResponseEntity<ApiResponse<CollectionModel<EntityModel<DetallePedido>>>> listar() {
        log.info("Petición HTTP: GET /api/detalle-pedido - Listando desgloses con HATEOAS.");
        List<DetallePedido> lista = service.listarTodos();

        List<EntityModel<DetallePedido>> hateoasItems = lista.stream()
                .map(detalle -> EntityModel.of(detalle,
                        linkTo(methodOn(DetallePedidoController.class).listar()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<DetallePedido>> model = CollectionModel.of(hateoasItems,
                linkTo(methodOn(DetallePedidoController.class).listar()).withSelfRel());

        return ResponseEntity.ok(new ApiResponse<>("Detalles obtenidos con éxito (HATEOAS)", model));
    }

    @PostMapping
    @Operation(summary = "Crear detalle (HATEOAS)", description = "Registra un ítem en el pedido y devuelve el recurso interactivo")
    public ResponseEntity<ApiResponse<EntityModel<DetallePedido>>> crear(@Valid @RequestBody DetallePedido detalle) {
        log.info("Petición HTTP: POST /api/detalle-pedido - Creando detalle con HATEOAS.");
        DetallePedido nuevoDetalle = service.guardar(detalle);

        EntityModel<DetallePedido> entityModel = EntityModel.of(nuevoDetalle,
                linkTo(methodOn(DetallePedidoController.class).listar()).withRel("ver-todos-los-detalles"));

        return new ResponseEntity<>(new ApiResponse<>("Detalle registrado correctamente (HATEOAS)", entityModel), HttpStatus.CREATED);
    }
}