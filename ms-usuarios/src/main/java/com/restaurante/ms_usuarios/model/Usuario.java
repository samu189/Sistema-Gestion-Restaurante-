package com.restaurante.ms_usuarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
@Schema(description = "Modelo operativo que representa a un miembro del personal o usuario del sistema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único e incremental generado por la base de datos", example = "15")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre completo del colaborador", example = "Alejandro Gómez")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Schema(description = "Correo electrónico que funciona como login", example = "alejandro.mesero@restaurante.com")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(description = "Clave de acceso encriptada del usuario", example = "$2a$10$eX7...")
    private String password;

    @NotBlank(message = "El rol es obligatorio (ADMIN, MESERO, CAJERO, COCINA)")
    @Schema(description = "Rol operativo que define los niveles de acceso del usuario", example = "MESERO")
    private String rol;
}