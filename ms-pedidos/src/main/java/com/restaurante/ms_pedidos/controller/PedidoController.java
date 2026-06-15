package com.restaurante.ms_pedidos.controller;

import com.restaurante.ms_pedidos.dto.PedidoDTO;
import com.restaurante.ms_pedidos.model.Pedido;
import com.restaurante.ms_pedidos.response.ApiResponse;
import com.restaurante.ms_pedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos Controller", description = "Controlador principal para el flujo de comandas y estado de pedidos con soporte HATEOAS")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    @Operation(summary = "Listar todas las órdenes (HATEOAS)", description = "Retorna el listado total de pedidos ingresados con enlaces hipermedia de navegación")
    public ResponseEntity<ApiResponse<CollectionModel<EntityModel<Pedido>>>> listar() {
        List<Pedido> pedidos = service.listarTodos();

        List<EntityModel<Pedido>> hateoasItems = pedidos.stream()
                .map(pedido -> EntityModel.of(pedido,
                        linkTo(methodOn(PedidoController.class).obtenerUno(pedido.getId())).withSelfRel(),
                        linkTo(methodOn(PedidoController.class).listar()).withRel("todos-los-pedidos")))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Pedido>> model = CollectionModel.of(hateoasItems,
                linkTo(methodOn(PedidoController.class).listar()).withSelfRel());

        return ResponseEntity.ok(new ApiResponse<>("Lista de pedidos obtenida con éxito (HATEOAS)", model));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una orden por ID (HATEOAS)", description = "Busca un pedido específico y añade links interactivos de control")
    public ResponseEntity<ApiResponse<EntityModel<Pedido>>> obtenerUno(@PathVariable Long id) {
        Pedido pedido = service.buscarPorId(id);

        EntityModel<Pedido> entityModel = EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).obtenerUno(id)).withSelfRel(),
                linkTo(methodOn(PedidoController.class).actualizarEstadoAPagado(id)).withRel("marcar-como-pagado"),
                linkTo(methodOn(PedidoController.class).listar()).withRel("ver-todos-los-pedidos"));

        return ResponseEntity.ok(new ApiResponse<>("Pedido encontrado (HATEOAS)", entityModel));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido (HATEOAS)", description = "Registra una nueva comanda desde el DTO y devuelve el recurso interactivo")
    public ResponseEntity<ApiResponse<EntityModel<Pedido>>> crear(@Valid @RequestBody PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setCliente(dto.getCliente());
        pedido.setProducto(dto.getProducto());
        pedido.setTotal(dto.getTotal());

        Pedido guardado = service.guardar(pedido);

        EntityModel<Pedido> entityModel = EntityModel.of(guardado,
                linkTo(methodOn(PedidoController.class).obtenerUno(guardado.getId())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listar()).withRel("ver-todos-los-pedidos"));

        return new ResponseEntity<>(new ApiResponse<>("Pedido creado exitosamente (HATEOAS)", entityModel), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/pagar")
    @Operation(summary = "Marcar pedido como pagado (Feign)", description = "Endpoint de integración inter-servicio utilizado por ms-pagos para cambiar el estado remotamente")
    public ResponseEntity<ApiResponse<Void>> actualizarEstadoAPagado(@PathVariable Long id) {
        service.marcarComoPagado(id);
        return ResponseEntity.ok(new ApiResponse<>("Estado del pedido actualizado a PAGADO remotamente", null));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido por ID", description = "Elimina un registro de la base de datos de manera definitiva")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>("Pedido eliminado correctamente", null));
    }
}