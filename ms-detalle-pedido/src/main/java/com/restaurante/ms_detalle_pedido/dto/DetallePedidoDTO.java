package com.restaurante.ms_detalle_pedido.dto;

import lombok.Data;

@Data
public class DetallePedidoDTO {
    private Long id;
    private Long pedidoId;
    private Long menuId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}