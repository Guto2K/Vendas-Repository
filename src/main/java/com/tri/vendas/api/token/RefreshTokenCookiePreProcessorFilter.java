package com.tri.vendas.api.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
// com isso n precisa mais passar o valor e parametro na header de refresh_token pelo postman clicando, ele faz sozinho automatico toda requisição de um novo refreshtoken e access.
//prioridade alta pq se for uma requisição que tenha granty_type refresh_token na requisição precisa adicionar o refreshtoken na header para aplicação funcionar a aplicação. 
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;

		  if ("/oauth/token".equalsIgnoreCase(req.getRequestURI())
		      && "refresh_token".equals(req.getParameter("grant_type"))
		      && req.getCookies() != null) {
			  
			  for (Cookie cookie : req.getCookies()) {
				  if(cookie.getName().equalsIgnoreCase("refreshToken")) {
					  
					  String refreshToken = cookie.getValue(); 
					  req = new MyServletRequestWrapper(req, refreshToken);
				  }


			  }
			  
		  }
		
		chain.doFilter(req, response);
		
	}
	
	
	//substitui a requesição, porque depois de feita n da pra alterar, recebe ela aqui copia e altera para ter o refreshtoken.
	static class MyServletRequestWrapper extends HttpServletRequestWrapper {

		private String refreshToken;
		
		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
			
		}
		
		
	

	//parametros da requisição, só vai colocar outro : refreshtoken
	@Override
	public Map<String, String[]> getParameterMap(){
		
		ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
		map.put("refresh_token", new String[] {refreshToken});
		map.setLocked(true);
		
		return map;
		}
	
	}
}
