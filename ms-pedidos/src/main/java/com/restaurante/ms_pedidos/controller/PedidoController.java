package com.restaurante.ms_pedidos.controller;

import com.restaurante.ms_pedidos.dto.PedidoDTO;
import com.restaurante.ms_pedidos.model.Pedido;
import com.restaurante.ms_pedidos.response.ApiResponse;
import com.restaurante.ms_pedidos.service.PedidoService;
import jakarta.validation.Valid; // Importante para las validaciones de la rúbrica
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Pedido>>> listar() {
        List<Pedido> pedidos = service.listarTodos();
        return ResponseEntity.ok(new ApiResponse<>("Lista de pedidos obtenida", pedidos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Pedido>> obtenerUno(@PathVariable Long id) {
        Pedido pedido = service.buscarPorId(id);
        return ResponseEntity.ok(new ApiResponse<>("Pedido encontrado", pedido));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Pedido>> crear(@Valid @RequestBody PedidoDTO dto) { // <--- Agregamos @Valid aquí
        // Convertimos el DTO a la Entidad (Model)
        Pedido pedido = new Pedido();
        pedido.setCliente(dto.getCliente());
        pedido.setProducto(dto.getProducto());
        pedido.setTotal(dto.getTotal());

        Pedido guardado = service.guardar(pedido);
        return new ResponseEntity<>(new ApiResponse<>("Pedido creado exitosamente", guardado), HttpStatus.CREATED);
    }

    // 🔥 ENDPOINT CLAVE PARA LA COMUNICACIÓN INTER-SERVICIO (FEIGN CLIENT)
    @PutMapping("/{id}/pagar")
    public ResponseEntity<ApiResponse<Void>> actualizarEstadoAPagado(@PathVariable Long id) {
        service.marcarComoPagado(id);
        return ResponseEntity.ok(new ApiResponse<>("Estado del pedido actualizado a PAGADO remotamente", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>("Pedido eliminado correctamente", null));
    }
}