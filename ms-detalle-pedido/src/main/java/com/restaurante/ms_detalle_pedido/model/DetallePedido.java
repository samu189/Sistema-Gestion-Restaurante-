package com.restaurante.ms_detalle_pedido.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "detalles_pedidos")
@Data
@Schema(description = "Modelo que representa un ítem individual dentro de un pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID auto-incremental generado por la base de datos", example = "1")
    private Long id;

    @NotNull(message = "El ID del pedido es obligatorio")
    @Schema(description = "ID del pedido maestro al que pertenece este detalle", example = "105")
    private Long pedidoId;

    @NotNull(message = "El ID del menú/producto es obligatorio")
    @Schema(description = "ID del plato o bebida del menú", example = "3")
    private Long menuId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima por detalle debe ser 1")
    @Schema(description = "Cantidad de unidades pedidas de este ítem", example = "2")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio unitario no puede ser negativo")
    @Schema(description = "Precio individual de cada unidad", example = "12.50")
    private Double precioUnitario;

    @NotNull(message = "El subtotal es obligatorio")
    @Min(value = 0, message = "El subtotal no puede ser negativo")
    @Schema(description = "Monto total calculado para este ítem (Cantidad x Precio)", example = "25.00")
    private Double subtotal;
}