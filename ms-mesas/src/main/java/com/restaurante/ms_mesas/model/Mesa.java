package com.restaurante.ms_mesas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "mesas")
@Data
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El número de mesa es obligatorio")
    @Min(value = 1, message = "El número de mesa debe ser igual o mayor a 1")
    private Integer numeroMesa;

    @NotNull(message = "La capacidad de la mesa es obligatoria")
    @Min(value = 1, message = "La capacidad mínima de la mesa debe ser para 1 persona")
    private Integer capacidad;

    @NotBlank(message = "El estado es obligatorio (DISPONIBLE, OCUPADA, RESERVADA)")
    private String estado = "DISPONIBLE";

    @NotBlank(message = "La ubicación es obligatoria (TERRAZA, SALON_PRINCIPAL, VIP)")
    private String ubicacion;
}
