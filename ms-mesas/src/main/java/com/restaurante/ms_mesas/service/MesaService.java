package com.restaurante.ms_mesas.service;

import com.restaurante.ms_mesas.model.Mesa;
import com.restaurante.ms_mesas.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MesaService {

    @Autowired
    private MesaRepository repository;

    public List<Mesa> listarMesas() {
        return repository.findAll();
    }

    public Mesa guardarMesa(Mesa mesa) {
        if (mesa.getEstado() == null) {
            mesa.setEstado("DISPONIBLE");
        }
        return repository.save(mesa);
    }
}
