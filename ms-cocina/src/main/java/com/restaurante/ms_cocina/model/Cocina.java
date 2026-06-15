package com.restaurante.ms_cocina.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "ordenes_cocina")
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "Entidad que representa una orden ingresada en la cocina")
public class Cocina extends RepresentationModel<Cocina> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la orden de cocina", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "La referencia al detalle del pedido es obligatoria")
    @Schema(description = "ID del detalle del pedido asociado", example = "12")
    private Long detallePedidoId;

    @NotBlank(message = "El nombre del plato no puede estar vacío")
    @Schema(description = "Nombre del plato a preparar", example = "Fettuccine Alfredo")
    private String nombrePlato;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima debe ser 1")
    @Schema(description = "Cantidad de platos solicitados", example = "2")
    private Integer cantidad;

    @Schema(description = "Notas especiales del cliente", example = "Sin sal y bien cocido")
    private String notas;

    @Schema(description = "Estado actual de la comanda en cocina", example = "PENDIENTE")
    private String estado = "PENDIENTE";
}