package com.tri.vendas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tri.vendas.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

}
