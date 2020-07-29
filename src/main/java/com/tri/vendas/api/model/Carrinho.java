package com.tri.vendas.api.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.xml.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

@Entity
@Table(name="carrinho")
public class Carrinho {
	
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name="codigo")
	private long id;
	
	//certo era criar uma classe pra guardar o valor total de 1 produto, qtda e outras coisas sendo uma list igual produto
	
	// esse vai sair daqui e vai ser la no Produto.
	//private Integer quantidade;
	
	@Column(name = "valorTotalProdutos")
	private BigDecimal valorTotalProdutos = new BigDecimal(0.00);

	//agregação, composição ? existe carrinho sem produto : vazio
	//aqui é ManytoMany : Um ou muitos carrinho podem ter 1 ou vários produtos, e 1 ou muitos produto(s) pode estar em 1 ou vários carrinhos.
	//@OneToMany(fetch = FetchType.EAGER)
	
	/*@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="usuario_permissao", joinColumns = @JoinColumn(name = "codigo_usuario")
	,inverseJoinColumns = @JoinColumn(name="codigo_permissao"))*/
	
	//@OneToMany
	//@JoinColumn(name = "codigo_produto")
	
	@JoinColumn(name="codigo_carrinho")
	@OneToMany(fetch = FetchType.EAGER)
	private List<Produto> produto = new ArrayList<Produto>();
	
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/*public Integer getQuantidade() {
		
		if (quantidade == null) {
			quantidade = 0;
		}
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}*/

	public BigDecimal getValorTotalProdutos() {
		return valorTotalProdutos;
	}

	public void setValorTotalProdutos(BigDecimal valorTotalProdutos) {
		this.valorTotalProdutos = valorTotalProdutos;
	}

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carrinho other = (Carrinho) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	public String toString(){
		return id+" "+valorTotalProdutos+" "+produto.toString();
	}
	
}
