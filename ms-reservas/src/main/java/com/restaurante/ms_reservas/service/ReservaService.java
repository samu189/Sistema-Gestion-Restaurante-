package com.restaurante.ms_reservas.service;

import com.restaurante.ms_reservas.model.Reserva;
import com.restaurante.ms_reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    public List<Reserva> listarTodas() {
        return repository.findAll();
    }

    public Reserva guardar(Reserva reserva) {
        if (reserva.getEstado() == null) {
            reserva.setEstado("CONFIRMADA"); // Estado por defecto
        }
        return repository.save(reserva);
    }
}
