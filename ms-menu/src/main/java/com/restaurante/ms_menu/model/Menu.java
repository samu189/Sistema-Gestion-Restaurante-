package com.restaurante.ms_menu.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "menu")
@Data
@Schema(description = "Modelo que representa un elemento o plato dentro de la carta del menú")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autoincremental de la base de datos", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del plato es obligatorio")
    @Schema(description = "Nombre comercial del plato o bebida", example = "Fettuccine Alfredo")
    private String nombre;

    @Schema(description = "Descripción de ingredientes o detalles del plato", example = "Pasta italiana cremosa con salsa blanca y queso parmesano")
    private String descripcion;

    @NotNull(message = "El precio del plato es obligatorio")
    @Min(value = 0, message = "El precio del plato no puede ser un valor negativo")
    @Schema(description = "Precio de venta al público", example = "14.90")
    private Double precio;

    @NotBlank(message = "La categoría es obligatoria (Entradas, Platos Fuertes, Bebidas, Postres)")
    @Schema(description = "Clasificación culinaria de la carta", example = "Platos Fuertes")
    private String categoria;

    @NotNull(message = "El estado de disponibilidad es obligatorio")
    @Schema(description = "Define si hay insumos para preparar el plato actualmente", example = "true")
    private Boolean disponible = true;
}