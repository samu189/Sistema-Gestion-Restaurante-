package com.restaurante.ms_menu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "menu")
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del plato es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio del plato es obligatorio")
    @Min(value = 0, message = "El precio del plato no puede ser un valor negativo")
    private Double precio;

    @NotBlank(message = "La categoría es obligatoria (Entradas, Platos Fuertes, Bebidas, Postres)")
    private String categoria;

    @NotNull(message = "El estado de disponibilidad es obligatorio")
    private Boolean disponible = true;
}
