package com.restaurante.ms_mesas.controller;

import com.restaurante.ms_mesas.model.Mesa;
import com.restaurante.ms_mesas.response.ApiResponse;
import com.restaurante.ms_mesas.service.MesaService;
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
@RequestMapping("/api/mesas")
@Slf4j
@Tag(name = "Mesas Controller", description = "Endpoints para el control del salón, reservas y estados de ocupación con HATEOAS")
public class MesaController {

    @Autowired
    private MesaService service;

    @GetMapping
    @Operation(summary = "Ver plano e inventario de mesas (HATEOAS)", description = "Retorna el listado completo de mesas y su estado actual con links de navegación")
    public ResponseEntity<ApiResponse<CollectionModel<EntityModel<Mesa>>>> listar() {
        log.info("Petición HTTP recibida: GET /api/mesas - Verificando el estado del mapa de mesas del salón.");
        List<Mesa> lista = service.listarMesas();

        // Envolvemos los datos con los enlaces autodescriptivos de HATEOAS
        List<EntityModel<Mesa>> hateoasItems = lista.stream()
                .map(mesa -> EntityModel.of(mesa,
                        linkTo(methodOn(MesaController.class).listar()).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Mesa>> model = CollectionModel.of(hateoasItems,
                linkTo(methodOn(MesaController.class).listar()).withSelfRel());

        return ResponseEntity.ok(new ApiResponse<>("Estado de las mesas recuperado con éxito (HATEOAS)", model));
    }

    @PostMapping
    @Operation(summary = "Dar de alta una nueva mesa (HATEOAS)", description = "Registra una mesa en una ubicación del local y devuelve el recurso interactivo")
    public ResponseEntity<ApiResponse<EntityModel<Mesa>>> crear(@Valid @RequestBody Mesa mesa) {
        log.info("Petición HTTP recibida: POST /api/mesas - Intentando registrar la Mesa N° {} en la zona '{}'",
                mesa.getNumeroMesa(), mesa.getUbicacion());
        Mesa nuevaMesa = service.guardarMesa(mesa);

        EntityModel<Mesa> entityModel = EntityModel.of(nuevaMesa,
                linkTo(methodOn(MesaController.class).listar()).withRel("ver-plano-de-mesas"));

        return new ResponseEntity<>(new ApiResponse<>("Mesa registrada con éxito (HATEOAS)", entityModel), HttpStatus.CREATED);
    }
}