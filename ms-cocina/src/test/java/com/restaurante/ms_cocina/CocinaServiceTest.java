package com.restaurante.ms_cocina;

import com.restaurante.ms_cocina.model.Cocina;
import com.restaurante.ms_cocina.repository.CocinaRepository;
import com.restaurante.ms_cocina.service.CocinaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CocinaServiceTest {

    @Mock
    private CocinaRepository repository;

    @InjectMocks
    private CocinaService cocinaService;

    @Test
    @DisplayName("Debería guardar una orden en cocina y asignar estado PENDIENTE por defecto si viene vacío")
    void deberiaGuardarOrdenAsignandoEstadoPendiente() {
        // GIVEN: Inicializamos una orden de entrada sin estado para forzar tu lógica interna
        Cocina ordenEntrada = new Cocina();
        ordenEntrada.setDetallePedidoId(12L);
        ordenEntrada.setNombrePlato("Fettuccine Alfredo");
        ordenEntrada.setCantidad(2);
        ordenEntrada.setNotas("Sin sal y bien cocido");
        ordenEntrada.setEstado(null); // Lo forzamos en null

        // Inicializamos el objeto que simulará retornar la base de datos ya guardado
        Cocina ordenGuardada = new Cocina();
        ordenGuardada.setId(1L);
        ordenGuardada.setDetallePedidoId(12L);
        ordenGuardada.setNombrePlato("Fettuccine Alfredo");
        ordenGuardada.setCantidad(2);
        ordenGuardada.setNotas("Sin sal y bien cocido");
        ordenGuardada.setEstado("PENDIENTE");

        // Entrenamos al Mock para que cuando se salve cualquier entidad Cocina, devuelva la guardada
        when(repository.save(any(Cocina.class))).thenReturn(ordenGuardada);

        // WHEN: Ejecutamos el método real 'guardarOrden' de tu servicio
        Cocina resultado = cocinaService.guardarOrden(ordenEntrada);

        // THEN: Validamos que la lógica interna de tu servicio haya funcionado impecable
        assertNotNull(resultado, "La orden guardada en cocina no debería ser nula");
        assertEquals(1L, resultado.getId());
        assertEquals(12L, resultado.getDetallePedidoId());
        assertEquals("Fettuccine Alfredo", resultado.getNombrePlato());
        assertEquals(2, resultado.getCantidad());
        assertEquals("PENDIENTE", resultado.getEstado(), "El servicio debió asignar PENDIENTE de forma automática");

        // Verificamos que se llamó al repositorio exactamente 1 vez
        verify(repository, times(1)).save(any(Cocina.class));
    }
}
