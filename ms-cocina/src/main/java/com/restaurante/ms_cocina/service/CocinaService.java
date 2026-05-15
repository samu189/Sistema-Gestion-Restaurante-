package com.restaurante.ms_cocina.service;

import com.restaurante.ms_cocina.model.Cocina;
import com.restaurante.ms_cocina.repository.CocinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CocinaService {

    @Autowired
    private CocinaRepository repository;

    public List<Cocina> listarOrdenes() {
        return repository.findAll();
    }

    public Cocina guardarOrden(Cocina cocina) {
        if (cocina.getEstado() == null || cocina.getEstado().isEmpty()) {
            cocina.setEstado("PENDIENTE");
        }
        return repository.save(cocina);
    }
}
