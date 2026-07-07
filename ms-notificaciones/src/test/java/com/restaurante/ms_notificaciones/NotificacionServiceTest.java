package com.restaurante.ms_notificaciones;

import com.restaurante.ms_notificaciones.model.Notificacion;
import com.restaurante.ms_notificaciones.repository.NotificacionRepository;
import com.restaurante.ms_notificaciones.service.NotificacionService;
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
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repository;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    @DisplayName("Debería registrar y enviar una notificación asignando la fecha de envío actual")
    void deberiaEnviarNotificacionExitosamente() {
        // GIVEN: Preparamos la notificación de entrada
        Notificacion notificacionEntrada = new Notificacion();
        notificacionEntrada.setDestinatario("cliente_vip@email.com");
        notificacionEntrada.setMensaje("Su pedido #45 ha salido de la cocina y va en camino.");
        notificacionEntrada.setTipo("CORREO");
        notificacionEntrada.setFechaEnvio(null); // Lo dejamos en null para probar que el servicio le asigne la fecha

        // Preparamos el objeto simulado que devolverá la persistencia
        Notificacion notificacionGuardada = new Notificacion();
        notificacionGuardada.setId(102L);
        notificacionGuardada.setDestinatario("cliente_vip@email.com");
        notificacionGuardada.setMensaje("Su pedido #45 ha salido de la cocina y va en camino.");
        notificacionGuardada.setTipo("CORREO");
        notificacionGuardada.setFechaEnvio(LocalDateTime.now());

        // Entrenamos al Mock del repositorio
        when(repository.save(any(Notificacion.class))).thenReturn(notificacionGuardada);

        // WHEN: Ejecutamos el método real del servicio
        Notificacion resultado = notificacionService.enviarNotificacion(notificacionEntrada);

        // THEN: Validamos que los datos retornados sean íntegros y la fecha se haya procesado
        assertNotNull(resultado, "La notificación procesada no debería ser nula");
        assertEquals(102L, resultado.getId());
        assertEquals("cliente_vip@email.com", resultado.getDestinatario());
        assertEquals("Su pedido #45 ha salido de la cocina y va en camino.", resultado.getMensaje());
        assertEquals("CORREO", resultado.getTipo());
        assertNotNull(resultado.getFechaEnvio(), "El servicio debió asignarle una fecha y hora de envío");

        // Verificamos la ejecución del repositorio una única vez
        verify(repository, times(1)).save(any(Notificacion.class));
    }
}
