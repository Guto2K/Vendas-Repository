package com.tri.vendas.api.repository.projection;

import java.math.BigDecimal;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.tri.vendas.api.model.Categoria;
import com.tri.vendas.api.model.Pessoa;

public class ResumoProduto {
	
	private Long codigo;
	private String nome;
	private String img;
	private BigDecimal preco;
	private Integer qtda;
	private String categoria;
	private String pessoa;
	
	
	
	public ResumoProduto(Long codigo, String nome, String img, BigDecimal preco, Integer qtda, String categoria,
			String pessoa) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.img = img;
		this.preco = preco;
		this.qtda = qtda;
		this.categoria = categoria;
		this.pessoa = pessoa;
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
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getPessoa() {
		return pessoa;
	}
	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}

	
	

}
