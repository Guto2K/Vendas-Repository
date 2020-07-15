package com.tri.vendas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tri.vendas.api.model.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho,Long> {

}
