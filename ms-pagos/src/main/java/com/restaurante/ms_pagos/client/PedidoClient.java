package com.restaurante.ms_pagos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "MS-PEDIDOS")
public interface PedidoClient {

    @PutMapping("/api/pedidos/{id}/pagar")
    void actualizarEstadoAPagado(@PathVariable("id") Long id);
}
