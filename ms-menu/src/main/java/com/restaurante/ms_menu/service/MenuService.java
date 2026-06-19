package com.restaurante.ms_menu.service;

import com.restaurante.ms_menu.model.Menu;
import com.restaurante.ms_menu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository repository;

    public List<Menu> listarTodo() {
        return repository.findAll();
    }

    public Menu guardar(Menu menu) {
        return repository.save(menu);
    }

    // AGREGADO: Buscar un plato específico por su ID para soporte inter-servicio
    public Menu buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}