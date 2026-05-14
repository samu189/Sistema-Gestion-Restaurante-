package com.restaurante.ms_inventario.dto;

import lombok.Data;

@Data
public class InventarioDTO {
    private String item;
    private Integer cantidad;
    private String unidadMedida;
}