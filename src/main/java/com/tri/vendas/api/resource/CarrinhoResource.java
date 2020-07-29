package com.tri.vendas.api.resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	private Carrinho carrinho = new Carrinho();

	
	
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	//tirar a logica do negocio daqui, criar uma service carrinho.
	@PostMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_GUARDAR_CARRINHO') and #oauth2.hasScope('write')")//fazer a role e banco
	public ResponseEntity<Carrinho> adicionarProdutoCarrinho (@PathVariable Long codigo){
		
		
		Optional<Produto> produtoSup = this.produtoRepository.findById(codigo);
		Produto produto = produtoSup.get();
		

		// erro de inicialização, n deu new no list produto do Carrinho em lugar algum, apenas deu new no Carrinho. é o mesmo erro do nullpointer do valor total que precisou por 0 de padrao, na video aula o cara faz isso. no meu caso deu na list tmb
		int controle = 0;
		//if (!carrinho.getProduto().isEmpty()) {
			//System.out.print("passou, passou, passou, passou esta vazia !");
		for (Produto x: carrinho.getProduto()) {
			if(x.getCodigo().equals(produto.getCodigo())) {
			
			x.setQtdaCompra(x.getQtdaCompra()+1);
			
			//carrinho.setValorTotalProdutos(new BigDecimal(0.00)); apagar
			
			// adicionando preço a um produto de acordo com numero de itens q tem:
			BigDecimal bd = new BigDecimal(x.getQtdaCompra());
			BigDecimal result = bd.multiply(produto.getPreco());
			x.setPrecoQtdaCompra(result.doubleValue());
			
			// valor total da compra
			carrinho.setValorTotalProdutos(carrinho.getValorTotalProdutos().add(produto.getPreco()));
			
			controle =1;
			break;
			}
		//}
		}
		 if(controle==0) {
			
		//da valor quantidade 1 pro novo produto
		produto.setQtdaCompra(1);	
		
		// adicionando preço a um produto de acordo com numero de itens q tem:
		BigDecimal bd = new BigDecimal(produto.getQtdaCompra());
		BigDecimal result = bd.multiply(produto.getPreco());
		produto.setPrecoQtdaCompra(result.doubleValue());
		
		//guardar produto no carrinho
				List<Produto> suporteListaProduto = new ArrayList<Produto>(); //ele apaga porque eu colo o list de produto no list rpoduto do carrinho ou seja ele substitui ao inves de juntar com oq ja tem, ou seja apaga os registros antertiores
				suporteListaProduto.add(produto);
				//carrinho.setProduto(suporteListaProduto); aqui o problema q apaga a list produto
				carrinho.getProduto().addAll(suporteListaProduto);
		//guardar valor total do item no total do carrinho
		carrinho.setValorTotalProdutos(carrinho.getValorTotalProdutos().add(produto.getPreco()));
		
		}
		
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
	//como esta no content ele n retorna carrinho kkkkkk n sabia, agora sei. mas o processo funciona, se for dar listar carrinho ve a mudança.
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_CARRINHO') and #oauth2.hasScope('write')")
	public Carrinho atualizarQuantidadeCarrinho(@PathVariable Long codigo, @PathVariable Integer acao){
		
		for (Produto x: carrinho.getProduto()) {
			if(x.getCodigo().equals(codigo)) {
				if(acao == 1){
					System.out.println("Entrou no 1");
					x.setQtdaCompra(x.getQtdaCompra()+1);
					carrinho.setValorTotalProdutos(carrinho.getValorTotalProdutos().add(x.getPreco()));

					}
				else if(acao == 0) {
					System.out.print("Entrou no 2");
					x.setQtdaCompra(x.getQtdaCompra()-1);		// tem validar pra n ir pra negativo
					carrinho.setValorTotalProdutos(carrinho.getValorTotalProdutos().subtract(x.getPreco()));

				}
				System.out.println("Setou preço unitario");
				// adicionando preço a um produto de acordo com numero de itens q tem:
				BigDecimal bd = new BigDecimal(x.getQtdaCompra());
				BigDecimal result = bd.multiply(x.getPreco());
				x.setPrecoQtdaCompra(result.doubleValue());
				
			}
			break;
		}		
		System.out.println(carrinho);
		return carrinho;
		//note que n ha total do item unitario, tipo : 2 fones = valor total apenas dos fones da compra. criar classe sem mapear que tem qtda, preçototalitem, tem q estar na carrinho e nela tem produto pra ela ter acesso aos dados do produto tipo o preço.
		
		/*Optional<Produto> produtoSup = this.produtoRepository.findById(codigo);
		Produto produto = produtoSup.get();
		
		BigDecimal bd = new BigDecimal(carrinho.getQuantidade());
		carrinho.setValorTotalProdutos(bd.multiply(produto.getPreco()));*/
	}
	
	
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)// n vai voltar nada
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CARRINHO') and #oauth2.hasScope('write')")
	public Carrinho removerProdutoCarrinho(@PathVariable Long codigo) {
	  
		for (Produto x: carrinho.getProduto()) {
			if(x.getCodigo().equals(codigo)) {
				//removendo por obj ao invés de index.
				carrinho.getProduto().remove(x);
				
				break;
			}
			
		}
		
		carrinho.setValorTotalProdutos(new BigDecimal(0.00));

		//tem q fazer isso ai embaixo pra atualizar o carrinho quando remover, diferente dos outros que era só somar +1 valor do produto ou subtrair 1 valor do produto
		//tem que chamar a calcular total dos pedidos do carrinho, que vai percorrer a list produtos do carrinho somando os valor total de cada obj produto ate o final da lista.
		
		for (Produto prod : carrinho.getProduto()) {
			
		BigDecimal bigDecTotal = new BigDecimal(prod.getPrecoQtdaCompra());
			
			carrinho.setValorTotalProdutos(carrinho.getValorTotalProdutos().add(bigDecTotal));
			
			}

		
		//errado pq vai retornar as headers e location erradas e há conflito com permissões/escopos...tem q mudar retorno para ResponseEntity e por o carrinho no Body.
		//this.listarCarrinho();
		return carrinho;
	}
	
	/*Experimento:
	public Carrinho carrinhoAtual() {
	    return this.carrinho;
	}*/
	
	/*@PostMapping("/finaliza")
	@PreAuthorize("hasAuthority('ROLE_GUARDAR_CARRINHO') and #oauth2.hasScope('write')")
	public ResponseEntity<Carrinho> finalizar(){
		
		carrinho
		
		.save
		
		return null;
	}*/


}