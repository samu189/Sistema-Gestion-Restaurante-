package com.restaurante.ms_pagos.service;

import com.restaurante.ms_pagos.model.Pago;
import com.restaurante.ms_pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository repository;

    public List<Pago> listarTodos() {
        return repository.findAll();
    }

    public Pago procesarPago(Pago pago) {
        pago.setFechaPago(LocalDateTime.now());
        if (pago.getEstado() == null || pago.getEstado().isEmpty()) {
            pago.setEstado("COMPLETADO");
        }
        return repository.save(pago);
    }
}
