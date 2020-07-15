package com.tri.vendas.api.resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tri.vendas.api.model.Carrinho;
import com.tri.vendas.api.model.Produto;
import com.tri.vendas.api.repository.ProdutoRepository;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoResource {
	
	//private List<Carrinho> listaCarrinho = new ArrayList<Carrinho>(); não precisa porque ja existe um arraylist de produto atributo na classe Carrinho. 
	public Carrinho carrinho = new Carrinho();
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	//tirar a logica do negocio daqui, criar uma service carrinho.
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_GUARDAR_CARRINHO') and #oauth2.hasScope('write')")//fazer a role e banco
	public ResponseEntity<Carrinho> adicionarProdutoCarrinho (@PathVariable Long codigo){
		
		
		Optional<Produto> produtoSup = this.produtoRepository.findById(codigo);
		Produto produto = produtoSup.get();
		
		int controle = 0;
		for (Produto x: carrinho.getProduto()) {
			if(x.getCodigo().equals(produto.getCodigo())) {
			
			x.setQtdaCompra(x.getQtdaCompra()+1);
			
			BigDecimal bd = new BigDecimal(x.getQtdaCompra());
			
			//carrinho.setValorTotalProdutos(new BigDecimal(0.00));
			
			carrinho.setValorTotalProdutos(carrinho.getValorTotalProdutos().add(produto.getPreco()));
			
			controle =1;
			break;
			}
		}
		if(controle==0) {
			
		//da valor quantidade 1 pro novo produto
		produto.setQtdaCompra(1);	
			
		//guardar produto no carrinho
		List<Produto> suporteListaProduto = new ArrayList<Produto>();
		suporteListaProduto.add(produto);
		carrinho.setProduto(suporteListaProduto);
		
		//guardar valor total do item no total do carrinho
		carrinho.setValorTotalProdutos(carrinho.getValorTotalProdutos().add(produto.getPreco()));
		
		}
		
		//calcular a lista valor total do carrinho, todos produtos, tem que criar classe pedidos para guardar ter esse total.
		
		
		//retorna o produto adicionado + os produtos adicionados antes
		return ResponseEntity.status(HttpStatus.OK).body(carrinho);
				
				
	}
	
	
	//implementar o event publisher pra header location e afins
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CARRINHO') and #oauth2.hasScope('read')")
	public Carrinho listarCarrinho(){
		
		return carrinho;
		
	}
	
	// fazer metodo pra validar se a quantidade n esta ja em 0, pois n pode ficar negativa
	@PutMapping("/{codigo}/alterar/{acao}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_CARRINHO') and #oauth2.hasScope('write')")
	public void atualizarQuantidadeCarrinho(@PathVariable Long codigo, @PathVariable Integer acao){
		
		for (Produto x: carrinho.getProduto()) {
			if(x.getCodigo().equals(codigo)) {
				if(acao == 1){
					x.setQtdaCompra(x.getQtdaCompra()+1);
					carrinho.setValorTotalProdutos(carrinho.getValorTotalProdutos().add(x.getPreco()));

					}
				else if(acao == 0) {
					x.setQtdaCompra(x.getQtdaCompra()-1);
					carrinho.setValorTotalProdutos(carrinho.getValorTotalProdutos().subtract(x.getPreco()));

				}
				break;
			}
		}
		
		//note que n ha total do item unitario, tipo : 2 fones = valor total apenas dos fones da compra. criar classe sem mapear que tem qtda, preçototalitem, tem q estar na carrinho e nela tem produto pra ela ter acesso aos dados do produto tipo o preço.
		
		/*Optional<Produto> produtoSup = this.produtoRepository.findById(codigo);
		Produto produto = produtoSup.get();
		
		BigDecimal bd = new BigDecimal(carrinho.getQuantidade());
		carrinho.setValorTotalProdutos(bd.multiply(produto.getPreco()));*/
	}
	
	
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CARRINHO') and #oauth2.hasScope('write')")
	public void removerProdutoCarrinho(@PathVariable Long codigo) {
	  
		for (Produto x: carrinho.getProduto()) {
			if(x.getCodigo().equals(codigo)) {
				//removendo por obj ao invés de index.
				carrinho.getProduto().remove(x);
				
				break;
			}
		}
		
		//tem que chamar a calcular total dos pedidos, que vai listar a carrinho inteira somando os valor total de cada obj carrinhoa té o final.
		
		//errado pq vai retornar as headers e location erradas...
		this.listarCarrinho();
	}


}