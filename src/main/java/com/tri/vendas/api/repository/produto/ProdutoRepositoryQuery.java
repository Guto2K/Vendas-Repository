package com.tri.vendas.api.repository.produto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tri.vendas.api.model.Produto;
import com.tri.vendas.api.repository.filter.ProdutoFilter;
import com.tri.vendas.api.repository.projection.ResumoProduto;

public interface ProdutoRepositoryQuery {
	
	public Page<Produto> filtrar(ProdutoFilter Produtofilter, Pageable pageable);
	
	public Page<ResumoProduto> resumir(ProdutoFilter produtoFilter, Pageable pageable);

}
