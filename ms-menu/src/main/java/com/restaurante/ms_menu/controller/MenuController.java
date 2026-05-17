package com.restaurante.ms_menu.controller;

import com.restaurante.ms_menu.model.Menu;
import com.restaurante.ms_menu.response.ApiResponse;
import com.restaurante.ms_menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@Slf4j // Logs para auditar la carta del restaurante
public class MenuController {

    @Autowired
    private MenuService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Menu>>> listar() {
        log.info("Petición HTTP: GET /api/menu - Consultando la carta completa del restaurante.");
        return ResponseEntity.ok(new ApiResponse<>("Menú obtenido con éxito", service.listarTodo()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Menu>> crear(@Valid @RequestBody Menu menu) {
        log.info("Petición HTTP: POST /api/menu - Añadiendo nuevo plato: '{}' en la categoría: '{}'",
                menu.getNombre(), menu.getCategoria());
        Menu nuevoPlato = service.guardar(menu);
        return new ResponseEntity<>(new ApiResponse<>("Plato añadido al menú con éxito", nuevoPlato), HttpStatus.CREATED);
    }
}
