package com.restaurante.ms_cocina.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "ordenes_cocina")
@Data
public class Cocina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La referencia al detalle del pedido es obligatoria")
    private Long detallePedidoId;

    @NotBlank(message = "El nombre del plato no puede estar vacío")
    private String nombrePlato;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima debe ser 1")
    private Integer cantidad;

    private String notas;         // Ej: "Sin sal", "Bien cocido"

    private String estado = "PENDIENTE"; // PENDIENTE, EN_PREPARACION, LISTO
}