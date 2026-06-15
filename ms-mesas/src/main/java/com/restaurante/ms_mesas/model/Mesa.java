package com.restaurante.ms_mesas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "mesas")
@Data
@Schema(description = "Modelo conceptual que representa una mesa física del restaurante")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único autogenerado en la BD", example = "1")
    private Long id;

    @NotNull(message = "El número de mesa es obligatorio")
    @Min(value = 1, message = "El número de mesa debe ser igual o mayor a 1")
    @Schema(description = "Número asignado a la mesa en el salón", example = "5")
    private Integer numeroMesa;

    @NotNull(message = "La capacidad de la mesa es obligatoria")
    @Min(value = 1, message = "La capacidad mínima de la mesa debe ser para 1 persona")
    @Schema(description = "Cantidad máxima de comensales permitidos", example = "4")
    private Integer capacidad;

    @NotBlank(message = "El estado es obligatorio (DISPONIBLE, OCUPADA, RESERVADA)")
    @Schema(description = "Estado actual operativo del recurso", example = "DISPONIBLE")
    private String estado = "DISPONIBLE";

    @NotBlank(message = "La ubicación es obligatoria (TERRAZA, SALON_PRINCIPAL, VIP)")
    @Schema(description = "Zona geográfica del local donde se encuentra la mesa", example = "TERRAZA")
    private String ubicacion;
}