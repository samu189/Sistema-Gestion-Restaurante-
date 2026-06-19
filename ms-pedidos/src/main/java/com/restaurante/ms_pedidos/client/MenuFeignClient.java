package com.restaurante.ms_pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import java.util.Map;

// "ms-menu" es el nombre exacto con el que registraste el otro microservicio en Eureka
@FeignClient(name = "ms-menu", path = "/api/menu")
public interface MenuFeignClient {

    // Llama al endpoint GET /api/menu/{id} que creamos hace un momento en ms-menu
    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable("id") Long id);
}
