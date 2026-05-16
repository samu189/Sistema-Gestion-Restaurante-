package com.restaurante.ms_notificaciones.service;

import com.restaurante.ms_notificaciones.model.Notificacion;
import com.restaurante.ms_notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    public List<Notificacion> obtenerHistorial() {
        return repository.findAll();
    }

    public Notificacion enviarNotificacion(Notificacion notificacion) {
        notificacion.setFechaEnvio(LocalDateTime.now());

        // Simulación de envío por consola
        System.out.println("====== [NOTIFICACIÓN ENVIADA] ======");
        System.out.println("Para: " + notificacion.getDestinatario());
        System.out.println("Mensaje: " + notificacion.getMensaje());
        System.out.println("====================================");

        return repository.save(notificacion);
    }
}