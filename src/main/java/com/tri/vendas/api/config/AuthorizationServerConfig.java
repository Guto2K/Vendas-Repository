package com.tri.vendas.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

 @Autowired
 private AuthenticationManager authenticationManager;
 
 @Autowired
 private UserDetailsService userDetailsService;
 
 
 @Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//poderia configurar para banco de dados tmb para servir varios clients, no caso vai servir só 1 o laravel/angular.
		clients.inMemory()
		.withClient("angular")
		.secret("$2a$10$anuQU6sekG42CCq5QUzLyun6G9J1SZk2wBv3v0KdBKDL0/Nv68y.y")
		.scopes("read","write")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(1800)
		.refreshTokenValiditySeconds(24*3600)
		.and()
		.withClient("m0b1l30")
		.secret("$2a$10$G1j5Rf8aEEiGc/AET9BA..xRR.qCpOUzBZoJd8ygbGy6tb3jsMT9G")
		.scopes("read")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(1800)
		.refreshTokenValiditySeconds(24*3600);
		
		
	}
	
 @Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		//armazenar token num tokenstore
		.tokenStore(tokenStore())
		//para configurar o Jwt********** sem ele usa access token normal.
		.accessTokenConverter(this.accessTokenConverter())
		//propriedade em que toda requisição gera um token e refreshtoken novo, assim nunca desloga enquanto estiver usando. Pelo menos se a pessoa usar 1 vez a cada dia que é o tempo do refreshtoken de vida.
		.reuseRefreshTokens(false)
		 .userDetailsService(this.userDetailsService)
		//pra validar
		.authenticationManager(authenticationManager);
	}
 
 @Bean
 public JwtAccessTokenConverter accessTokenConverter() {
	 JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
	 accessTokenConverter.setSigningKey("algaworks");
	 
	 return accessTokenConverter;
}
 

/* @Bean
    public PasswordEncoder passwordEncoder() {
    	
	return NoOpPasswordEncoder.getInstance();
    }*/

@Bean
 public TokenStore tokenStore() {
	 
	 return new JwtTokenStore(accessTokenConverter());
 }
}