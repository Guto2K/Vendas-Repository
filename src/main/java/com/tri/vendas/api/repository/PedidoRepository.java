package com.tri.vendas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tri.vendas.api.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {

}
