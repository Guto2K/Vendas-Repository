package com.tri.vendas.api.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="pedidos")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="codigo")
	private long id;
	
	@NotNull
	private Double valorTotalCompra = 0.00;
	
	@NotNull
	private String formaDePagamento = "Dinheiro"; // TODO caso de uso novo para pagamento
	/* não posso deixar notnull pois não há registro do carrinho no banco
	@NotNull*/
	@OneToOne
	@JoinColumn(name = "codigo_carrinho")
	private Carrinho carrinho;
	
	@JsonFormat(pattern =  "dd/MM/yyyy")
	@Column(name = "data_compra")
	private LocalDate dataCompra;
	
	
	

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getValorTotalCompra() {
		return valorTotalCompra;
	}

	public void setValorTotalCompra(Double valorTotalCompra) {
		this.valorTotalCompra = valorTotalCompra;
	}

	public String getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}
	
	
	

}
