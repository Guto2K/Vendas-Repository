package com.tri.vendas.api.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Carrinho.class)
public abstract class Carrinho_ {

	public static volatile SingularAttribute<Carrinho, BigDecimal> valorTotalProdutos;
	public static volatile ListAttribute<Carrinho, Produto> produto;
	public static volatile SingularAttribute<Carrinho, Long> id;

	public static final String VALOR_TOTAL_PRODUTOS = "valorTotalProdutos";
	public static final String PRODUTO = "produto";
	public static final String ID = "id";

}

