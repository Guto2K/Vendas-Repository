package com.tri.vendas.api.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pedido.class)
public abstract class Pedido_ {

	public static volatile SingularAttribute<Pedido, Double> valorTotalCompra;
	public static volatile SingularAttribute<Pedido, Long> id;
	public static volatile SingularAttribute<Pedido, String> formaDePagamento;
	public static volatile SingularAttribute<Pedido, Carrinho> carrinho;
	public static volatile SingularAttribute<Pedido, LocalDate> dataCompra;

	public static final String VALOR_TOTAL_COMPRA = "valorTotalCompra";
	public static final String ID = "id";
	public static final String FORMA_DE_PAGAMENTO = "formaDePagamento";
	public static final String CARRINHO = "carrinho";
	public static final String DATA_COMPRA = "dataCompra";

}

