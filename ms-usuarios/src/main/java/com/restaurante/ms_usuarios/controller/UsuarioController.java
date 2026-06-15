package com.restaurante.ms_usuarios.controller;

import com.restaurante.ms_usuarios.dto.UsuarioRequestDTO;
import com.restaurante.ms_usuarios.model.Usuario;
import com.restaurante.ms_usuarios.response.ApiResponse;
import com.restaurante.ms_usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuarios Controller", description = "Endpoints para el control de accesos, registro de personal y roles del sistema con soporte HATEOAS")
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    @Operation(summary = "Listar personal registrado (HATEOAS)", description = "Retorna la nómina completa de usuarios del sistema con enlaces autodescriptivos de navegación")
    public ResponseEntity<ApiResponse<CollectionModel<EntityModel<Usuario>>>> listar() {
        log.info("Petición HTTP recibida: GET /api/usuarios - Consultando la lista de personal del sistema.");
        List<Usuario> lista = service.listarTodos();

        // Envolvemos las entidades en contenedores interactivos HATEOAS
        List<EntityModel<Usuario>> hateoasItems = lista.stream()
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(UsuarioController.class).listar()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Usuario>> model = CollectionModel.of(hateoasItems,
                linkTo(methodOn(UsuarioController.class).listar()).withSelfRel());

        return ResponseEntity.ok(new ApiResponse<>("Lista de usuarios obtenida con éxito (HATEOAS)", model));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo usuario (HATEOAS)", description = "Crea una cuenta para el personal del restaurante y devuelve el recurso interactivo")
    public ResponseEntity<ApiResponse<EntityModel<Usuario>>> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        log.info("Petición HTTP recibida: POST /api/usuarios - Intentando registrar usuario con email: '{}' y rol: '{}'",
                dto.getEmail(), dto.getRol());
        Usuario nuevoUsuario = service.guardar(dto);

        EntityModel<Usuario> entityModel = EntityModel.of(nuevoUsuario,
                linkTo(methodOn(UsuarioController.class).listar()).withRel("ver-lista-de-usuarios"));

        return new ResponseEntity<>(new ApiResponse<>("Usuario creado con éxito (HATEOAS)", entityModel), HttpStatus.CREATED);
    }
}