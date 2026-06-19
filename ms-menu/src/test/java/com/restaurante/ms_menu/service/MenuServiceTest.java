package com.restaurante.ms_menu.service;

import com.restaurante.ms_menu.model.Menu;
import com.restaurante.ms_menu.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    private MenuRepository repository;

    @InjectMocks
    private MenuService menuService;

    @Test
    @DisplayName("Debería guardar un plato en el menú correctamente")
    void deberiaGuardarPlatoExitosamente() {
        // GIVEN: Inicializamos un plato de prueba simulado
        Menu platoEntrada = new Menu();
        platoEntrada.setNombre("Lomo Saltado");
        platoEntrada.setPrecio(45.0);
        platoEntrada.setDisponible(true);

        Menu platoGuardado = new Menu();
        platoGuardado.setId(1L);
        platoGuardado.setNombre("Lomo Saltado");
        platoGuardado.setPrecio(45.0);
        platoGuardado.setDisponible(true);

        // Simulamos la respuesta del repositorio
        when(repository.save(any(Menu.class))).thenReturn(platoGuardado);

        // WHEN: Ejecutamos la acción del servicio
        Menu resultado = menuService.guardar(platoEntrada);

        // THEN: Validamos que los datos retornados sean correctos
        assertNotNull(resultado, "El plato guardado no debería retornar nulo");
        assertEquals(1L, resultado.getId());
        assertEquals("Lomo Saltado", resultado.getNombre());
        assertTrue(resultado.getDisponible(), "El plato debería estar disponible por defecto");

        // Verificamos que se haya invocado al repositorio exactamente 1 vez
        verify(repository, times(1)).save(platoEntrada);
    }
}