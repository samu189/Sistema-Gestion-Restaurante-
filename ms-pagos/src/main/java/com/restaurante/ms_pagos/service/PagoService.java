package com.restaurante.ms_pagos.service;

import com.restaurante.ms_pagos.client.PedidoClient; // Importamos el cliente que creaste en el Paso 1
import com.restaurante.ms_pagos.model.Pago;
import com.restaurante.ms_pagos.repository.PagoRepository;
import lombok.extern.slf4j.Slf4j; // Para los logs de la rúbrica
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j // Activamos la consola de logs
public class PagoService {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private PedidoClient pedidoClient; // Inyectamos el puente de comunicación remota

    public List<Pago> listarTodos() {
        log.info("Buscando todos los registros de pago en la base de datos.");
        List<Pago> pagos = repository.findAll();
        log.info("Total de pagos recuperados: {}", pagos.size());
        return pagos;
    }

    public Pago procesarPago(Pago pago) {
        log.info("Iniciando procesamiento de pago en la capa de negocio.");
        pago.setFechaPago(LocalDateTime.now());

        if (pago.getEstado() == null || pago.getEstado().isEmpty()) {
            pago.setEstado("COMPLETADO");
        }

        // 1. Guardamos primero el pago localmente en la base de datos de ms-pagos
        Pago pagoGuardado = repository.save(pago);
        log.info("Pago registrado con éxito en BD local. ID de Pago asignado: {}", pagoGuardado.getId());

        // 2. 🔥 COMUNICACIÓN REMOTA: Le avisamos de inmediato a ms-pedidos
        try {
            log.info("Iniciando llamada remota vía OpenFeign hacia ms-pedidos para el Pedido ID: {}", pago.getPedidoId());
            pedidoClient.actualizarEstadoAPagado(pago.getPedidoId());
            log.info("Comunicación inter-servicio exitosa: ms-pedidos ha sido notificado.");
        } catch (Exception e) {
            // Si ms-pedidos falla o está caído, este catch evita que ms-pagos tire error 500
            log.error("¡ALERTA! Error al intentar comunicar con ms-pedidos de forma remota: {}", e.getMessage());
        }

        return pagoGuardado;
    }
}
