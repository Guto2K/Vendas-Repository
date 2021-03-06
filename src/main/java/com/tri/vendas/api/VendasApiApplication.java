package com.tri.vendas.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.tri.vendas.api.config.property.VendasApiProperty;

//inicia a aplicação spring pelo metodo main, todas as pastas e classes dentro do msmo pacote dessa main serão auto configuradas por conta da anotação abaixo.
@SpringBootApplication
@EnableConfigurationProperties(VendasApiProperty.class)
public class VendasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendasApiApplication.class, args);
	}

}
