package com.restaurante.ms_detalle_pedido.service;

import com.restaurante.ms_detalle_pedido.model.DetallePedido;
import com.restaurante.ms_detalle_pedido.repository.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository repository;

    public List<DetallePedido> listarTodos() {
        return repository.findAll();
    }

    public DetallePedido guardar(DetallePedido detalle) {
        // Lógica: Calcular subtotal (Cantidad * Precio)
        if (detalle.getCantidad() != null && detalle.getPrecioUnitario() != null) {
            detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
        }
        return repository.save(detalle);
    }
}
