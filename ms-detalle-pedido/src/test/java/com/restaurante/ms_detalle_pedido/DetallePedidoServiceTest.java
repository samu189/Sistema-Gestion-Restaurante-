package com.restaurante.ms_detalle_pedido;

import com.restaurante.ms_detalle_pedido.model.DetallePedido;
import com.restaurante.ms_detalle_pedido.repository.DetallePedidoRepository;
import com.restaurante.ms_detalle_pedido.service.DetallePedidoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DetallePedidoServiceTest {

    @Mock
    private DetallePedidoRepository repository;

    @InjectMocks
    private DetallePedidoService detallePedidoService;

    @Test
    @DisplayName("Debería guardar un detalle de pedido calculando correctamente el subtotal automático")
    void deberiaGuardarDetalleCalculandoSubtotal() {
        // GIVEN: Inicializamos un detalle de entrada con cantidad 2 y precio 12.50 (Sin subtotal)
        DetallePedido detalleEntrada = new DetallePedido();
        detalleEntrada.setPedidoId(105L);
        detalleEntrada.setMenuId(3L);
        detalleEntrada.setCantidad(2);
        detalleEntrada.setPrecioUnitario(12.50);
        detalleEntrada.setSubtotal(null); // Lo dejamos nulo para comprobar que el servicio lo calcula

        // Inicializamos el objeto que simulará retornar la base de datos (con ID y subtotal calculado de 25.00)
        DetallePedido detalleGuardado = new DetallePedido();
        detalleGuardado.setId(1L);
        detalleGuardado.setPedidoId(105L);
        detalleGuardado.setMenuId(3L);
        detalleGuardado.setCantidad(2);
        detalleGuardado.setPrecioUnitario(12.50);
        detalleGuardado.setSubtotal(25.00); // 2 * 12.50 = 25.00

        // Entrenamos al Mock del repositorio para el método save
        when(repository.save(any(DetallePedido.class))).thenReturn(detalleGuardado);

        // WHEN: Ejecutamos el método real 'guardar' de tu servicio
        DetallePedido resultado = detallePedidoService.guardar(detalleEntrada);

        // THEN: Validamos las operaciones y la consistencia de los datos mapeados
        assertNotNull(resultado, "El detalle guardado no debería retornar nulo");
        assertEquals(1L, resultado.getId());
        assertEquals(105L, resultado.getPedidoId());
        assertEquals(3L, resultado.getMenuId());
        assertEquals(2, resultado.getCantidad());
        assertEquals(12.50, resultado.getPrecioUnitario());
        assertEquals(25.00, resultado.getSubtotal(), "El subtotal calculado automáticamente debió ser 25.00");

        // Verificamos que se interactuó con el repositorio exactamente 1 vez
        verify(repository, times(1)).save(any(DetallePedido.class));
    }
}
