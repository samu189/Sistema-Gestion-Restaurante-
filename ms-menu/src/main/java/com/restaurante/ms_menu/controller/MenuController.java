package com.restaurante.ms_menu.controller;

import com.restaurante.ms_menu.model.Menu;
import com.restaurante.ms_menu.response.ApiResponse;
import com.restaurante.ms_menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
@Slf4j
@Tag(name = "Menú Controller", description = "Endpoints para gestionar la carta gastronómica del restaurante con soporte HATEOAS")
public class MenuController {

    @Autowired
    private MenuService service;

    @GetMapping
    @Operation(summary = "Consultar la carta completa (HATEOAS)", description = "Obtiene todos los platos, bebidas y postres de la carta incluyendo sus enlaces de navegación")
    public ResponseEntity<ApiResponse<CollectionModel<EntityModel<Menu>>>> listar() {
        log.info("Petición HTTP: GET /api/menu - Consultando la carta completa del restaurante.");
        List<Menu> lista = service.listarTodo();

        // Envolvemos cada plato con HATEOAS agregando su link interactivo self
        List<EntityModel<Menu>> hateoasItems = lista.stream()
                .map(plato -> EntityModel.of(plato,
                        linkTo(methodOn(MenuController.class).listar()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Menu>> model = CollectionModel.of(hateoasItems,
                linkTo(methodOn(MenuController.class).listar()).withSelfRel());

        return ResponseEntity.ok(new ApiResponse<>("Menú obtenido con éxito (HATEOAS)", model));
    }

    @PostMapping
    @Operation(summary = "Añadir un nuevo plato (HATEOAS)", description = "Agrega un ítem a la carta y retorna el recurso con enlaces HATEOAS")
    public ResponseEntity<ApiResponse<EntityModel<Menu>>> crear(@Valid @RequestBody Menu menu) {
        log.info("Petición HTTP: POST /api/menu - Añadiendo nuevo plato: '{}' en la categoría: '{}'",
                menu.getNombre(), menu.getCategoria());
        Menu nuevoPlato = service.guardar(menu);

        EntityModel<Menu> entityModel = EntityModel.of(nuevoPlato,
                linkTo(methodOn(MenuController.class).listar()).withRel("ver-toda-la-carta"));

        return new ResponseEntity<>(new ApiResponse<>("Plato añadido al menú con éxito (HATEOAS)", entityModel), HttpStatus.CREATED);
    }
}