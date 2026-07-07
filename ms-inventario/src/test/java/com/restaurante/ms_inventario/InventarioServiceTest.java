package com.restaurante.ms_inventario;

import com.restaurante.ms_inventario.model.Inventario;
import com.restaurante.ms_inventario.repository.InventarioRepository;
import com.restaurante.ms_inventario.dto.InventarioDTO;
import com.restaurante.ms_inventario.service.InventarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository repository;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    @DisplayName("Debería registrar un insumo en el inventario correctamente a partir de un DTO")
    void deberiaCrearInsumoExitosamente() {
        // GIVEN: Inicializamos el DTO
        InventarioDTO dtoInsumo = new InventarioDTO();
        dtoInsumo.setItem("Harina de Trigo");
        dtoInsumo.setCantidad(50);
        dtoInsumo.setUnidadMedida("Kg");

        // Inicializamos la entidad esperada
        Inventario insumoGuardado = new Inventario();
        insumoGuardado.setId(1L);
        insumoGuardado.setItem("Harina de Trigo");
        insumoGuardado.setCantidad(50);
        insumoGuardado.setUnidadMedida("Kg");

        // Entrenamos al Mock
        when(repository.save(any(Inventario.class))).thenReturn(insumoGuardado);

        // WHEN: Ejecutamos el método
        Inventario resultado = inventarioService.crear(dtoInsumo);

        // THEN: Validaciones
        assertNotNull(resultado, "El insumo guardado no debería retornar nulo");
        assertEquals(1L, resultado.getId());
        assertEquals("Harina de Trigo", resultado.getItem());
        assertEquals(50, resultado.getCantidad());
        assertEquals("Kg", resultado.getUnidadMedida());

        verify(repository, times(1)).save(any(Inventario.class));
    }
}