package com.restaurante.ms_inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "inventario")
@Data
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del ítem es obligatorio")
    private String item; // Ejemplo: "Harina", "Coca Cola"

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad en inventario no puede ser negativa")
    private Integer cantidad;

    @NotBlank(message = "La unidad de medida es obligatoria")
    private String unidadMedida; // Ejemplo: "Kg", "Unidades"
}