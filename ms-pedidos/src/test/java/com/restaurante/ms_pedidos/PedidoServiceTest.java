package com.restaurante.ms_pedidos;

import com.restaurante.ms_pedidos.client.MenuFeignClient;
import com.restaurante.ms_pedidos.model.Pedido;
import com.restaurante.ms_pedidos.repository.PedidoRepository;
import com.restaurante.ms_pedidos.service.PedidoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private MenuFeignClient menuFeignClient; // Simulamos el cliente remoto Feign

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    @DisplayName("Debería registrar un pedido simulando comunicación exitosa con ms-menu")
    void deberiaGuardarPedidoConValidacionExitosa() {
        // GIVEN: Preparamos los datos de prueba
        Pedido pedidoEntrada = new Pedido();
        pedidoEntrada.setCliente("Samuel");
        pedidoEntrada.setEstado("PENDIENTE");

        Pedido pedidoGuardado = new Pedido();
        pedidoGuardado.setId(100L);
        pedidoGuardado.setCliente("Samuel");
        pedidoGuardado.setEstado("PENDIENTE");

        // Simulamos la respuesta ficticia que devolvería ms-menu a través de Feign
        Map<String, Object> respuestaMenuSimulada = new HashMap<>();
        respuestaMenuSimulada.put("id", 1L);
        respuestaMenuSimulada.put("nombre", "Lomo Saltado");
        respuestaMenuSimulada.put("precio", 45.0);
        ResponseEntity<Map<String, Object>> responseEntity = ResponseEntity.ok(respuestaMenuSimulada);

        // Comportamiento de los Mocks
        when(menuFeignClient.buscarPorId(anyLong())).thenReturn(responseEntity);
        when(repository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

        // WHEN: Ejecutamos el método a testear
        Pedido resultado = pedidoService.guardar(pedidoEntrada);

        // THEN: Verificaciones y aserciones
        assertNotNull(resultado, "El pedido guardado no debería ser nulo");
        assertEquals(100L, resultado.getId());
        assertEquals("Samuel", resultado.getCliente());
        assertEquals("PENDIENTE", resultado.getEstado());

        // Verificamos que se invocó tanto al cliente Feign como al repositorio local
        verify(menuFeignClient, times(1)).buscarPorId(1L);
        verify(repository, times(1)).save(pedidoEntrada);
    }
}
