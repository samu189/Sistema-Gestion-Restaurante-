package com.restaurante.ms_inventario.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "inventario")
@Data
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item; // Ejemplo: "Harina", "Coca Cola"
    private Integer cantidad;
    private String unidadMedida; // Ejemplo: "Kg", "Unidades"
}