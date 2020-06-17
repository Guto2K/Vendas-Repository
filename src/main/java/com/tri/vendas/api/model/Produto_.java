package com.tri.vendas.api.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Produto.class)
public abstract class Produto_ {

	public static volatile SingularAttribute<Produto, BigDecimal> preco;
	public static volatile SingularAttribute<Produto, Long> codigo;
	public static volatile SingularAttribute<Produto, String> img;
	public static volatile SingularAttribute<Produto, Pessoa> pessoa;
	public static volatile SingularAttribute<Produto, Integer> qtda;
	public static volatile SingularAttribute<Produto, Categoria> categoria;
	public static volatile SingularAttribute<Produto, String> nome;

	public static final String PRECO = "preco";
	public static final String CODIGO = "codigo";
	public static final String IMG = "img";
	public static final String PESSOA = "pessoa";
	public static final String QTDA = "qtda";
	public static final String CATEGORIA = "categoria";
	public static final String NOME = "nome";

}

