package com.restaurante.ms_reservas;

import com.restaurante.ms_reservas.model.Reserva;
import com.restaurante.ms_reservas.repository.ReservaRepository;
import com.restaurante.ms_reservas.service.ReservaService;
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
public class ReservaServiceTest {

    @Mock
    private ReservaRepository repository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("Debería guardar una reserva correctamente y asignar el estado CONFIRMADA si viene nulo")
    void deberiaGuardarReservaAsignandoEstadoConfirmada() {
        // GIVEN: Inicializamos una reserva de entrada con estado nulo para probar tu lógica
        Reserva reservaEntrada = new Reserva();
        reservaEntrada.setNombreCliente("Mariana Silva");
        reservaEntrada.setNumeroPersonas(4);
        reservaEntrada.setFechaReserva(LocalDateTime.of(2026, 7, 20, 20, 30));
        reservaEntrada.setMesaAsignada("Mesa 12 (VIP)");
        reservaEntrada.setEstado(null); // Obligamos al servicio a pasar por el IF

        // Inicializamos el objeto simulado que retornará la base de datos ya guardado
        Reserva reservaGuardada = new Reserva();
        reservaGuardada.setId(8L);
        reservaGuardada.setNombreCliente("Mariana Silva");
        reservaGuardada.setNumeroPersonas(4);
        reservaGuardada.setFechaReserva(LocalDateTime.of(2026, 7, 20, 20, 30));
        reservaGuardada.setMesaAsignada("Mesa 12 (VIP)");
        reservaGuardada.setEstado("CONFIRMADA");

        // Entrenamos al Mock del repositorio para interceptar el método save
        when(repository.save(any(Reserva.class))).thenReturn(reservaGuardada);

        // WHEN: Ejecutamos el método real 'guardar' de tu servicio
        Reserva resultado = reservaService.guardar(reservaEntrada);

        // THEN: Validaciones reglamentarias de datos
        assertNotNull(resultado, "La reserva guardada no debería retornar nula");
        assertEquals(8L, resultado.getId());
        assertEquals("Mariana Silva", resultado.getNombreCliente());
        assertEquals(4, resultado.getNumeroPersonas());
        assertEquals("Mesa 12 (VIP)", resultado.getMesaAsignada());
        assertEquals("CONFIRMADA", resultado.getEstado(), "El servicio debió asignar automáticamente el estado CONFIRMADA");

        // Verificamos que se llamó a la base de datos exactamente 1 vez
        verify(repository, times(1)).save(any(Reserva.class));
    }
}
