package com.restaurante.ms_inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "inventario")
@Data
@Schema(description = "Modelo que representa un insumo o materia prima dentro de la bodega")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autogenerado en la base de datos", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del ítem es obligatorio")
    @Schema(description = "Nombre o descripción detallada del insumo", example = "Harina de Trigo")
    private String item;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad en inventario no puede ser negativa")
    @Schema(description = "Stock físico actual disponible en almacén", example = "50")
    private Integer cantidad;

    @NotBlank(message = "La unidad de medida es obligatoria")
    @Schema(description = "Unidad de cuantificación física del insumo", example = "Kg")
    private String unidadMedida;
}