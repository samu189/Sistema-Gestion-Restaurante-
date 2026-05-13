package com.restaurante.ms_usuarios.service;

import com.restaurante.ms_usuarios.dto.UsuarioRequestDTO;
import com.restaurante.ms_usuarios.model.Usuario;
import com.restaurante.ms_usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {
    private final UsuarioRepository repository;

    public List<Usuario> listarTodos() {
        log.info("Consultando todos los usuarios");
        return repository.findAll();
    }

    public Usuario guardar(UsuarioRequestDTO dto) {
        log.info("Registrando nuevo usuario con email: {}", dto.getEmail());
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        return repository.save(usuario);
    }
}
