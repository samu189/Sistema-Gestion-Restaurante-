package com.restaurante.ms_pagos;

import com.restaurante.ms_pagos.client.PedidoClient;
import com.restaurante.ms_pagos.model.Pago;
import com.restaurante.ms_pagos.repository.PagoRepository;
import com.restaurante.ms_pagos.service.PagoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @Mock
    private PagoRepository repository;

    @Mock
    private PedidoClient pedidoClient; // Simulamos el cliente remoto Feign

    @InjectMocks
    private PagoService pagoService;

    @Test
    @DisplayName("Debería procesar un pago exitosamente asignando estado COMPLETADO y notificando a ms-pedidos")
    void deberiaProcesarPagoExitosamente() {
        // GIVEN: Preparamos la transacción de entrada sin estado ni fecha
        Pago pagoEntrada = new Pago();
        pagoEntrada.setPedidoId(1001L);
        pagoEntrada.setMonto(35.50);
        pagoEntrada.setMetodoPago("TARJETA");
        pagoEntrada.setEstado(null); // Obligamos al servicio a asignarle "COMPLETADO"

        // Preparamos el objeto guardado que simulará retornar el repositorio local
        Pago pagoGuardado = new Pago();
        pagoGuardado.setId(45L);
        pagoGuardado.setPedidoId(1001L);
        pagoGuardado.setMonto(35.50);
        pagoGuardado.setMetodoPago("TARJETA");
        pagoGuardado.setEstado("COMPLETADO");
        pagoGuardado.setFechaPago(LocalDateTime.now());

        // Comportamiento de los Mocks
        when(repository.save(any(Pago.class))).thenReturn(pagoGuardado);
        // Simulamos que la llamada remota de Feign se ejecuta limpiamente sin lanzar excepciones
        doNothing().when(pedidoClient).actualizarEstadoAPagado(1001L);

        // WHEN: Ejecutamos el método de negocio de procesamiento de pagos
        Pago resultado = pagoService.procesarPago(pagoEntrada);

        // THEN: Validaciones rigurosas
        assertNotNull(resultado, "El pago procesado no debería retornar nulo");
        assertEquals(45L, resultado.getId());
        assertEquals(1001L, resultado.getPedidoId());
        assertEquals(35.50, resultado.getMonto());
        assertEquals("TARJETA", resultado.getMetodoPago());
        assertEquals("COMPLETADO", resultado.getEstado(), "El servicio debió forzar el estado a COMPLETADO");
        assertNotNull(resultado.getFechaPago(), "El servicio debió fijar la marca de tiempo del cobro");

        // Verificamos que se invocó tanto la base de datos local como la API remota vía Feign de forma síncrona
        verify(repository, times(1)).save(any(Pago.class));
        verify(pedidoClient, times(1)).actualizarEstadoAPagado(1001L);
    }
}
