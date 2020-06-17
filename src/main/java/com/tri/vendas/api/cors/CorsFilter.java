package com.tri.vendas.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
//precisa do filtro pq o oauth2 n da acesso mesmo com permissao do cors, n tem integração boa com o security spring ainda.ele passa pela origin, mas o oauth2 n da acesso com token
public class CorsFilter implements Filter {

	private String originPermitida = "http://localhost:8000";
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		
		//para as origens permitidas
		response.setHeader("Access-Control-Allow-Origin", originPermitida);
		//para permitir credenciais de segurança como o Cookie
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		
		if ("OPTIONAL".equals(request.getMethod())&& originPermitida.equals(request.getHeader("Origin"))) {
			//metodos permitidos
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
//as headers autorizadas nas requisições
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");

			response.setHeader("Access-Control-Max-Age", "3600");

			
			response.setStatus(HttpServletResponse.SC_OK);
			
		}else {
			chain.doFilter(req, resp);
		}
		
		
		
	}

}
