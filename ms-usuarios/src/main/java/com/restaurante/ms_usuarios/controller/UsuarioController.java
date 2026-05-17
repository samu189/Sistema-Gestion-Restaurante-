package com.restaurante.ms_usuarios.controller;

import com.restaurante.ms_usuarios.dto.UsuarioRequestDTO;
import com.restaurante.ms_usuarios.model.Usuario;
import com.restaurante.ms_usuarios.response.ApiResponse;
import com.restaurante.ms_usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j // Logs para control de accesos y seguridad de usuarios
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Usuario>>> listar() {
        log.info("Petición HTTP recibida: GET /api/usuarios - Consultando la lista de personal del sistema.");
        return ResponseEntity.ok(new ApiResponse<>("Lista de usuarios obtenida con éxito", service.listarTodos()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("Petición HTTP recibida: POST /api/usuarios - Intentando registrar usuario con email: '{}' y rol: '{}'",
                dto.getEmail(), dto.getRol());
        Usuario nuevoUsuario = service.guardar(dto);
        return new ResponseEntity<>(new ApiResponse<>("Usuario creado con éxito", nuevoUsuario), HttpStatus.CREATED);
    }
}
