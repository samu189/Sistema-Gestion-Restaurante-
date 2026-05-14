package com.restaurante.ms_menu.controller;

import com.restaurante.ms_menu.model.Menu;
import com.restaurante.ms_menu.response.ApiResponse;
import com.restaurante.ms_menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Menu>>> listar() {
        return ResponseEntity.ok(new ApiResponse<>("Menú obtenido", service.listarTodo()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Menu>> crear(@RequestBody Menu menu) {
        return ResponseEntity.ok(new ApiResponse<>("Plato añadido al menú", service.guardar(menu)));
    }
}
