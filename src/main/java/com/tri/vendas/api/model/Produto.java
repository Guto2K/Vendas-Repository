package com.tri.vendas.api.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "produto")
public class Produto {
	
	//falta validar os campos com @NotNull ou @Size do bean validation.
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull
	private String nome;
	 
	private String img;
	
	@NotNull
	private BigDecimal preco;
	
	//quantidade estoque
	@NotNull
	private Integer qtda;
	
	//@JsonIgnore  tem q tirar senao o jackson n retorna o json desse campo la no postman e em outro lugar
	@Transient
	private int qtdaCompra = 0;
	
	//@JsonIgnore
	@Transient
	private Double precoQtdaCompra = 0.00;
	
	@NotNull
	//1 ou muitos produtos para 1 categoria
	@ManyToOne
	//coluna que faz a referência no banco:
	@JoinColumn(name = "codigo_categoria")
	private Categoria categoria;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_pessoa")
	private Pessoa pessoa;
	
	

	
	public Double getPrecoQtdaCompra() {
		return precoQtdaCompra;
	}

	public void setPrecoQtdaCompra(Double precoQtdaCompra) {
		this.precoQtdaCompra = precoQtdaCompra;
	}

	public int getQtdaCompra() {
		return qtdaCompra;
	}

	public void setQtdaCompra(int qtdaCompra) {
		this.qtdaCompra = qtdaCompra;
	}

	
	
	
	
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Integer getQtda() {
		return qtda;
	}

	public void setQtda(Integer qtda) {
		this.qtda = qtda;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
	
	public String toString(){
		return codigo+" "+nome+" "+preco+" "+qtda;
	}
	
	

}
