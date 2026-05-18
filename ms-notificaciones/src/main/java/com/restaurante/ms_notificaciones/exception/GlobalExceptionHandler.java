package com.restaurante.ms_notificaciones.exception;

import com.restaurante.ms_notificaciones.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 🟢 NUEVO: Este es tu "filtro especializado" para limpiar los errores de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extrae exactamente el mensaje que pusiste en tu anotación (ej: @NotBlank)
        String mensajeLimpio = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>("Error de validación: " + mensajeLimpio, null));
    }

    // 🟡 EL QUE YA TENÍAS: Atrapa cualquier otro error crítico del servidor
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("Error en el módulo de notificaciones: " + ex.getMessage(), null));
    }
}