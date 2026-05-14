package com.restaurante.ms_menu.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "menu")
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private String categoria; // Entradas, Platos Fuertes, Bebidas, Postres
    private Boolean disponible; // Para quitar platos si se acaba el ingrediente
}
