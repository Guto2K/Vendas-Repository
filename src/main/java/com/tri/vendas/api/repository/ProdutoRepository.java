package com.tri.vendas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tri.vendas.api.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

}
