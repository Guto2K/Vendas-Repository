package com.tri.vendas.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tri.vendas.api.event.RecursoCriadoEvent;
import com.tri.vendas.api.exceptionhandler.VendasExceptionHandler.Erro;
import com.tri.vendas.api.model.Produto;
import com.tri.vendas.api.repository.ProdutoRepository;
import com.tri.vendas.api.repository.filter.ProdutoFilter;
import com.tri.vendas.api.repository.projection.ResumoProduto;
import com.tri.vendas.api.service.ProdutoService;
import com.tri.vendas.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private MessageSource messageSource;
	
	 //REMOÇÃO DE PRODUTO.
	
	@GetMapping
/*public List<Produto> pesquisar (ProdutoFilter produtoFilter){
		
		return produtoRepository.filtrar(produtoFilter);
	}*/
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PRODUTO') and #oauth2.hasScope('read')")
	public Page<Produto> pesquisar (ProdutoFilter produtoFilter, Pageable pageable){
		
		return produtoRepository.filtrar(produtoFilter, pageable);
	}
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PRODUTO') and #oauth2.hasScope('read')")
	public Page<ResumoProduto> resumir (ProdutoFilter produtoFilter, Pageable pageable){
		
		return produtoRepository.resumir(produtoFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PRODUTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Produto> buscarPeloCodigo (@PathVariable Long codigo){
		
		Optional<Produto> produto = this.produtoRepository.findById(codigo);
		
		return produto.isPresent()?
				ResponseEntity.ok(produto.get()) : ResponseEntity.notFound().build();
	}
	
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PRODUTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Produto> criar (@RequestBody @Valid Produto produto, HttpServletResponse response){
		
		Produto produtoSalvo = produtoService.salvar(produto);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
		
	}
	
	
	// esta aqui e n na serviceadvice pq ela é referente apenas a pessoa, enquanto as de la capturam exceções de toda aplicação.
	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
		
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros =Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PRODUTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		
		//sem this. tmb funfa.
	  this.produtoRepository.deleteById(codigo);
	}

}
