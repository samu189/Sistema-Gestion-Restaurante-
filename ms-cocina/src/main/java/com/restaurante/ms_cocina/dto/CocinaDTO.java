package com.restaurante.ms_cocina.dto;

import lombok.Data;

@Data
public class CocinaDTO {
    private Long id;
    private Long detallePedidoId;
    private String nombrePlato;
    private Integer cantidad;
    private String notas;
    private String estado;
}
