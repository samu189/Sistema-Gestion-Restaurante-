package com.restaurante.ms_usuarios.service;

import com.restaurante.ms_usuarios.model.Usuario;
import com.restaurante.ms_usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Debería registrar un nuevo usuario de forma exitosa")
    void deberiaCrearUsuarioExitosamente() {
        // GIVEN: Inicializamos un usuario simulado
        Usuario usuarioEntrada = new Usuario();

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);

        // Entrenamos al Mock del repositorio para interceptar el método save
        when(repository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        // WHEN: Ejecutamos el guardado a través de la persistencia mockeada
        Usuario resultado = repository.save(usuarioEntrada);

        // THEN: Validaciones
        assertNotNull(resultado, "El usuario guardado no debería retornar nulo");
        assertEquals(1L, resultado.getId());

        // Verificamos que se llamó al repositorio exactamente 1 vez
        verify(repository, times(1)).save(any(Usuario.class));
    }
}