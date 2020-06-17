package com.tri.vendas.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

//habilitar segurança
@SuppressWarnings("deprecation")
//@EnableWebSecurity
@Configuration
//oauth2
@EnableResourceServer
//WebSecurityConfigurerAdapter do basic
//habilita a segurança nos metodos resource
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	/*@Autowired
	private UserDetailsService userDetailsService;*/
	
	/*
	 //@Override antes no basic, mudou os 2 metodos para public após importar o extends da classe.
	 
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		//no password encoder, precisa deixar explicito : "{noop}admin" --------- é retirado apos criar a Oauthsecurityconfig porque la ele ja define como nopasswordencoder = noop.
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());/*auth.inMemoryAuthentication()
		.withUser("admin").password("admin").roles("ROLE");
	}*/
	


	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//todas requisições em categorias sao permitidas sem autentificação
		.antMatchers("/categorias").permitAll()
		// qualquer outra requisição precisa passar nos headers HTTP a autentificação.
		.anyRequest().authenticated()
		.and()
		//vai mudar para oauth
		//.httpBasic().and()
		//rest n guarda estado de nada, n tem sessão nenhuma no servidor
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		//desabilita o javascript injection no serviço web
		.csrf().disable();
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}
	
	/*@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}*/
	
	//metodo de permissões ROLE
	@Bean
	public MethodSecurityExpressionHandler createExpressionHandler() {
		
		return new OAuth2MethodSecurityExpressionHandler();
	}
}