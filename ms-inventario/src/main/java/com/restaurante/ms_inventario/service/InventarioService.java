package com.restaurante.ms_inventario.service;

import com.restaurante.ms_inventario.model.Inventario;
import com.restaurante.ms_inventario.repository.InventarioRepository;
import com.restaurante.ms_inventario.dto.InventarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventarioService {
    @Autowired
    private InventarioRepository repository;

    public List<Inventario> listarTodo() { return repository.findAll(); }

    public Inventario crear(InventarioDTO dto) {
        Inventario inv = new Inventario();
        inv.setItem(dto.getItem());
        inv.setCantidad(dto.getCantidad());
        inv.setUnidadMedida(dto.getUnidadMedida());
        return repository.save(inv);
    }
}