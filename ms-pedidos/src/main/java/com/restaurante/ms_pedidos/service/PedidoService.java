package com.restaurante.ms_pedidos.service;

import com.restaurante.ms_pedidos.model.Pedido;
import com.restaurante.ms_pedidos.exception.ResourceNotFoundException;
import com.restaurante.ms_pedidos.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j; // Importante para usar los logs de la rúbrica
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j // Agrega la anotación para activar el objeto 'log'
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    // Listar todos los pedidos
    public List<Pedido> listarTodos() {
        log.info("Consultando la lista completa de pedidos en la base de datos.");
        List<Pedido> pedidos = repository.findAll();
        log.info("Se encontraron {} pedidos en total.", pedidos.size());
        return pedidos;
    }

    // Buscar un pedido por ID
    public Pedido buscarPorId(Long id) {
        log.info("Buscando pedido en la base de datos con ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error: Pedido con ID {} no fue encontrado.", id);
                    return new ResourceNotFoundException("Pedido con ID " + id + " no encontrado");
                });
    }

    // Guardar un nuevo pedido
    public Pedido guardar(Pedido pedido) {
        log.info("Registrando un nuevo pedido para el cliente: {}", pedido.getCliente());
        // Por si acaso, si tus pedidos inician como pendientes en la base de datos:
        // pedido.setEstado("PENDIENTE");
        Pedido pedidoGuardado = repository.save(pedido);
        log.info("Pedido guardado exitosamente con el ID: {}", pedidoGuardado.getId());
        return pedidoGuardado;
    }

    // 🔥 MÉTODO REMOTO CLAVE: Modifica el estado del pedido cuando ms-pagos le avisa
    public void marcarComoPagado(Long id) {
        log.info("Llamada remota recibida de ms-pagos para procesar el Pedido ID: {}", id);

        // Buscamos el pedido usando el método que ya tiene control de excepciones
        Pedido pedido = buscarPorId(id);

        // Cambiamos el estado (asegúrate de que tu entity tenga este setter)
        pedido.setEstado("PAGADO");
        repository.save(pedido);

        log.info("El pedido con ID: {} ha cambiado exitosamente su estado a [PAGADO].", id);
    }

    // Eliminar un pedido
    public void eliminar(Long id) {
        log.info("Solicitud para eliminar el pedido con ID: {}", id);
        if (!repository.existsById(id)) {
            log.error("Error al eliminar: El pedido con ID {} no existe.", id);
            throw new ResourceNotFoundException("No se puede eliminar: El pedido con ID " + id + " no existe");
        }
        repository.deleteById(id);
        log.info("Pedido con ID: {} eliminado correctamente de la base de datos.", id);
    }
}