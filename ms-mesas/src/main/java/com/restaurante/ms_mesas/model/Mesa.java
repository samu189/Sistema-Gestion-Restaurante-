package com.restaurante.ms_mesas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mesas")
@Data
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroMesa;
    private Integer capacidad;   // Ej: 4 personas, 2 personas
    private String estado;       // DISPONIBLE, OCUPADA, RESERVADA
    private String ubicacion;    // TERRAZA, SALON_PRINCIPAL, VIP
}
