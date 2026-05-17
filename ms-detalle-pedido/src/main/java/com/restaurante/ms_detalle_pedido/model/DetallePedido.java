package com.restaurante.ms_detalle_pedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "detalles_pedidos")
@Data
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del pedido es obligatorio")
    private Long pedidoId;

    @NotNull(message = "El ID del menú/producto es obligatorio")
    private Long menuId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima por detalle debe ser 1")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio unitario no puede ser negativo")
    private Double precioUnitario;

    @NotNull(message = "El subtotal es obligatorio")
    @Min(value = 0, message = "El subtotal no puede ser negativo")
    private Double subtotal;
}