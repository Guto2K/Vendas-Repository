package com.tri.vendas.api.resource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tri.vendas.api.model.Carrinho;
import com.tri.vendas.api.model.Pedido;
import com.tri.vendas.api.repository.CarrinhoRepository;
import com.tri.vendas.api.repository.PedidoRepository;

@RestController
@RequestMapping("/pedido")
public class PedidoResource {
	
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CarrinhoRepository carrinhoRepository;
	
	
	// mudar para responseEntity, precisa das headers e location
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_GRAVAR_PEDIDO') and #oauth2.hasScope('write')") //implementar o permissao de pedido nos usuarios maria e adm para testar
	public void gravar (@RequestBody Carrinho carrinho){
		// tenho que salvar carrinho antes de salvar pedido para ter referencia de um carrinho, importar repository aqui de carrinho para fazer isso...
		
		
		carrinhoRepository.save(carrinho);
		//talvez criar service de pedidos para esses calculos de pedidos
		Pedido pedido = new Pedido();
		
		pedido.setCarrinho(carrinho);
		pedido.setDataCompra(LocalDate.now());
		pedido.setFormaDePagamento("Dinheiro");
		pedido.setValorTotalCompra(carrinho.getValorTotalProdutos().doubleValue());
		
		
		pedidoRepository.save(pedido);
		//TODO criar classe e tabela itens que tem produtos e a quantidade de cada um deles no carrinho bem como preço total desse procuto ou unitario se tiver só um no carrinho
		
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PEDIDO') and #oauth2.hasScope('read')")
	public ResponseEntity<List<Pedido>> visualizar (){
		
		/* Experimento: CarrinhoResource carrinhoPedido = new CarrinhoResource();
		Carrinho carrinho = carrinhoPedido.carrinhoAtual();
		*/
		
		List<Pedido> pedidos =pedidoRepository.findAll();
		
		
		
		return ResponseEntity.status(HttpStatus.FOUND).body(pedidos);
	}

}
