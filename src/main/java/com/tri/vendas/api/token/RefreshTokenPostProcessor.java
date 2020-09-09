package com.tri.vendas.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.tri.vendas.api.config.property.VendasApiProperty;

@ControllerAdvice
// é o tipo de objto que deve ser interceptado antes da resposta da requisição : <OAuth2AccessToken>
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Autowired
	private VendasApiProperty vendasApiProperty;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		 return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();

		//transforma nesse obj default pq nele tem o metodo de apagar o token do body.
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		
		String refreshToken = body.getRefreshToken().getValue();
		
		// adicionar no cookie
		adicionarRefreshTokenNoCokkie(refreshToken,req,resp);
		
		//remover do body
		removerRefreshTokenDoBody(token);
		
		return body;
	}

	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
	
		token.setRefreshToken(null);
		
	}

	private void adicionarRefreshTokenNoCokkie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		
		// nome do cookie "refreshToken"
		Cookie refreshTokenCookie = new Cookie("refreshToken",refreshToken);
		//só é acessivel em http
		refreshTokenCookie.setHttpOnly(true);
		// false por enquanto, em produção deve ser Https ou seja TRUE
		refreshTokenCookie.setSecure(vendasApiProperty.getSeguranca().isEnableHttps());
		//para qual caminho esse cookie deve ser direcionado automaticamente pelo browser ?
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
		//em 30 dias o cookie expira sozinho
		refreshTokenCookie.setMaxAge(2592000);
		// add cookie na resposta 
		resp.addCookie(refreshTokenCookie);
		
	}
	

}
