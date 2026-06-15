package com.restaurante.ms_pedidos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo transaccional que representa un pedido o comanda realizada en el restaurante")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único autoincremental de la orden", example = "10")
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio y no puede estar vacío")
    @Schema(description = "Nombre o referencia del cliente que solicita el pedido", example = "Carlos Mendoza")
    private String cliente;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Schema(description = "Nombre comercial del producto consumido", example = "Hamburguesa con Queso")
    private String producto;

    @NotNull(message = "El total del pedido es obligatorio")
    @Min(value = 1, message = "El total del pedido debe ser como mínimo 1")
    @Schema(description = "Monto monetario acumulado de la comanda", example = "18.50")
    private Double total;

    @Schema(description = "Estado actual del ciclo de vida de la comanda", example = "PENDIENTE")
    private String estado = "PENDIENTE";
}