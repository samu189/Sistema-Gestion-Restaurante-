package com.restaurante.ms_pedidos.model;

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
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio y no puede estar vacío")
    private String cliente;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String producto;

    @NotNull(message = "El total del pedido es obligatorio")
    @Min(value = 1, message = "El total del pedido debe ser como mínimo 1")
    private Double total;

    private String estado = "PENDIENTE";
}