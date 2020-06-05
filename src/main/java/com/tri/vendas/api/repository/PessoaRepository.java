package com.tri.vendas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tri.vendas.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa,Long> {

}
