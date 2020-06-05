package com.tri.vendas.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tri.vendas.api.model.Pessoa;
import com.tri.vendas.api.model.Produto;
import com.tri.vendas.api.repository.PessoaRepository;
import com.tri.vendas.api.repository.ProdutoRepository;
import com.tri.vendas.api.service.exception.PessoaInexistenteOuInativaException;


@Service
public class ProdutoService {
	
	// fazer com relacionamento, mas depois mudar para tirar o Pessoa de produto e pegar pelo usuario o 
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	public Produto salvar(Produto produto) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(produto.getPessoa().getCodigo());
		if(pessoa == null|| pessoa.get().isInativo()) {
			
			throw new PessoaInexistenteOuInativaException();
			
		}
		
		return produtoRepository.save(produto);
	}
	
	
	

}
