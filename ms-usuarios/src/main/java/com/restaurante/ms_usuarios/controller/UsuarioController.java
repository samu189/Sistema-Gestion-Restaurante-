package com.restaurante.ms_usuarios.controller;

import com.restaurante.ms_usuarios.dto.UsuarioRequestDTO;
import com.restaurante.ms_usuarios.model.Usuario;
import com.restaurante.ms_usuarios.response.ApiResponse;
import com.restaurante.ms_usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Usuario>> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario nuevoUsuario = service.guardar(dto);
        return ResponseEntity.status(201).body(ApiResponse.success("Usuario creado con éxito", nuevoUsuario));
    }
}
