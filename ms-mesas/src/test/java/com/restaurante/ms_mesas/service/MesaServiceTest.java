package com.restaurante.ms_mesas.service;

import com.restaurante.ms_mesas.model.Mesa;
import com.restaurante.ms_mesas.repository.MesaRepository;
import com.restaurante.ms_mesas.service.MesaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MesaServiceTest {

    @Mock
    private MesaRepository repository;

    @InjectMocks
    private MesaService mesaService;

    @Test
    @DisplayName("Debería registrar una mesa correctamente con estado por defecto")
    void deberiaGuardarMesaExitosamente() {
        // GIVEN: Inicializamos una mesa de prueba simulada
        Mesa mesaEntrada = new Mesa();
        mesaEntrada.setNumeroMesa(5);
        mesaEntrada.setCapacidad(4);
        mesaEntrada.setUbicacion("TERRAZA");

        Mesa mesaGuardada = new Mesa();
        mesaGuardada.setId(1L);
        mesaGuardada.setNumeroMesa(5);
        mesaGuardada.setCapacidad(4);
        mesaGuardada.setEstado("DISPONIBLE");
        mesaGuardada.setUbicacion("TERRAZA");

        // Simulamos la respuesta del repositorio simulado
        when(repository.save(any(Mesa.class))).thenReturn(mesaGuardada);

        // WHEN: Ejecutamos la acción del servicio real
        Mesa resultado = mesaService.guardarMesa(mesaEntrada);

        // THEN: Validamos las afirmaciones de seguridad requeridas
        assertNotNull(resultado, "El objeto guardado no debería retornar nulo");
        assertEquals(1L, resultado.getId());
        assertEquals("DISPONIBLE", resultado.getEstado(), "Debería asignar estado DISPONIBLE por defecto");
        assertEquals("TERRAZA", resultado.getUbicacion());

        // Verificamos que se haya invocado al repositorio exactamente 1 vez
        verify(repository, times(1)).save(mesaEntrada);
    }
}