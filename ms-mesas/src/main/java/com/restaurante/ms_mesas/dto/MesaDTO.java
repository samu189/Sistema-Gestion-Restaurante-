package com.restaurante.ms_mesas.dto;

import lombok.Data;

@Data
public class MesaDTO {
    private Long id;
    private Integer numeroMesa;
    private Integer capacidad;
    private String estado;
    private String ubicacion;
}
