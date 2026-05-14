package com.restaurante.ms_pedidos.service;

import com.restaurante.ms_pedidos.model.Pedido;
import com.restaurante.ms_pedidos.exception.ResourceNotFoundException;
import com.restaurante.ms_pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    // Listar todos los pedidos
    public List<Pedido> listarTodos() {
        return repository.findAll();
    }

    // Buscar un pedido por ID (si no existe, lanza nuestra excepción personalizada)
    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con ID " + id + " no encontrado"));
    }

    // Guardar un nuevo pedido
    public Pedido guardar(Pedido pedido) {
        return repository.save(pedido);
    }

    // Eliminar un pedido
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: El pedido con ID " + id + " no existe");
        }
        repository.deleteById(id);
    }
}