package com.restaurante.ms_menu.dto;

import lombok.Data;

@Data
public class MenuDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String categoria;
    private Boolean disponible;
}
