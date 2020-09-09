package com.tri.vendas.api.repository.produto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.tri.vendas.api.model.Categoria_;
import com.tri.vendas.api.model.Pessoa_;
import com.tri.vendas.api.model.Produto;
import com.tri.vendas.api.model.Produto_;
import com.tri.vendas.api.repository.filter.ProdutoFilter;
import com.tri.vendas.api.repository.projection.ResumoProduto;

public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery {

	//para poder trabalhar com consultas no banco
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	/* public List<Produto> filtrar(ProdutoFilter produtoFilter) */
	public Page<Produto> filtrar(ProdutoFilter produtoFilter, Pageable pageable) {
	
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Produto> criteriaQuery = builder.createQuery(Produto.class);
		
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		//restrições e ordenações
		
		Predicate[] predicates = criarRestricoes(produtoFilter, builder, root);
		
		criteriaQuery.where(predicates);
		
		Order[] orders = criarOrdenacao(produtoFilter, builder, root);
		
		criteriaQuery.orderBy(orders);
		
		//------------
		TypedQuery<Produto> query = manager.createQuery(criteriaQuery);
		
		//paginação : tem que passar a "query" pq ela tem o resultado da pesquisa no banco atraves do getresult, resultset
		
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		
		return new PageImpl<>(query.getResultList(), pageable, total(produtoFilter));
	}
	
	@Override
	public Page<ResumoProduto> resumir(ProdutoFilter produtoFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoProduto> criteria = builder.createQuery(ResumoProduto.class);
		Root<Produto> root = criteria.from(Produto.class);
		
		criteria.select(builder.construct(ResumoProduto.class
				, root.get(Produto_.CODIGO), root.get(Produto_.NOME)
				, root.get(Produto_.IMG), root.get(Produto_.PRECO)
				, root.get(Produto_.QTDA), root.get(Produto_.CATEGORIA).get(Categoria_.NOME)
				, root.get(Produto_.PESSOA).get(Pessoa_.NOME)));
		
		
		Predicate[] predicates = criarRestricoes(produtoFilter, builder, root);
		
		criteria.where(predicates);
		
		Order[] orders = criarOrdenacao(produtoFilter, builder, root);
		
		criteria.orderBy(orders);
		
		//------------
		TypedQuery<ResumoProduto> query = manager.createQuery(criteria);
		
		//paginação : tem que passar a "query" pq ela tem o resultado da pesquisa no banco atraves do getresult, resultset
		
		
		adicionarRestricoesDePaginacao(query, pageable);
		
		
		return new PageImpl<>(query.getResultList(), pageable, total(produtoFilter));
	}
	
	
//para contar quantos registros tem
	private Long total(ProdutoFilter produtoFilter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Produto> root = criteria.from(Produto.class);
		
		Predicate[] predicates = criarRestricoes(produtoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}


	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
		
	}
	
	
	

	private Order[] criarOrdenacao(ProdutoFilter produtoFilter, CriteriaBuilder builder, Root<Produto> root) {
		
		List<Order> orders = new ArrayList<>();
		
		  if (!StringUtils.isEmpty(produtoFilter.getPreco()) && produtoFilter.getPreco() == true) {
		
		orders.add(builder.asc(root.get(Produto_.PRECO)));
    	
	}
		
		return orders.toArray(new Order[orders.size()]);
	}

	private Predicate[] criarRestricoes(ProdutoFilter produtoFilter, CriteriaBuilder builder, Root<Produto> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		// where nome like '%dsadas%'
		
		if (!StringUtils.isEmpty(produtoFilter.getNome())) {

			predicates.add(
					builder.like(builder.lower(root.get(Produto_.NOME)), "%" + produtoFilter.getNome().toLowerCase()+"%"));
			
		}
		/* TEM QUE FAZER BUSCA DE PRODUTO POR CATEGORIA, MUDAR FILTRO, ADD ATRIBUTO CATEGORIA PRA FAZER A BUSCA
        if (!StringUtils.isEmpty(produtoFilter.getPreco()) && produtoFilter.getPreco() == true) {
			
			predicates.add(builder.like(builder.lower((root.get, pattern)));
        	
		}*/
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}



}
