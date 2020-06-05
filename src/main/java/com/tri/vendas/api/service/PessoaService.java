package com.tri.vendas.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.tri.vendas.api.model.Pessoa;
import com.tri.vendas.api.repository.PessoaRepository;

//para o spring reconhecer como um component, poder injetar em qualquer classe.
@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {

		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);

		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");

		return this.pessoaRepository.save(pessoaSalva);
	}


	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		
		pessoaSalva.setAtivo(ativo);
		
		pessoaRepository.save(pessoaSalva);
		
	}
	
	//passou de private para public pra usar o buscar pessoa por id no service de produto pra verificar se a pessoa ta ativa ou n.
	public Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaSalva = this.pessoaRepository.findById(codigo)
			.orElseThrow(() -> new EmptyResultDataAccessException(1));
		return pessoaSalva;
	}
	
}
