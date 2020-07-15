/*carrinho para produto é many to many */
/* nunca vai ter salvamento de carrinho, apenas de pedidos, não precisaria criar a tabela se n tivesse mapeado ORM da classe carrinho*/
CREATE TABLE carrinho (
	codigo BIGINT(20) PRIMARY KEY,
	quantidade INT(50),
	valorTotalProdutos DECIMAL(10,2),
	/*codigo_produto BIGINT(20) essa propriedade fica na tabela produto e tera q por uma FK para codigo da carrinho aqui, note que tera que dar alter table para mudar isso*/
	/*codigo_produto BIGINT(20)*//* modelo se fosse onetomany*//*talvez precise disso para conexao entre as tabelas pois quando é manytoone ela tem a codigoFK que conecta na codigo da outra, mas aqui tem apenas a codigo la e a fk ?*/
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



ALTER TABLE produto
 ADD COLUMN codigo_carrinho BIGINT(20)
 ;
 
 ALTER TABLE produto
 ADD CONSTRAINT FK_carrinho_produto FOREIGN KEY (codigo_carrinho) REFERENCES carrinho(codigo)
 ;
 
INSERT INTO permissao (codigo, descricao) values (9, 'ROLE_GUARDAR_CARRINHO');
INSERT INTO permissao (codigo, descricao) values (10, 'ROLE_REMOVER_CARRINHO');
INSERT INTO permissao (codigo, descricao) values (11, 'ROLE_PESQUISAR_CARRINHO');
INSERT INTO permissao (codigo, descricao) values (12, 'ROLE_ATUALIZAR_CARRINHO');


/*CREATE TABLE carrinho_produto (
	codigo_carrinho BIGINT(20) NOT NULL,
	codigo_produto BIGINT(20) NOT NULL,
	PRIMARY KEY (codigo_carrinho, codigo_produto),
	FOREIGN KEY (codigo_carrinho) REFERENCES carrinho(codigo),
	FOREIGN KEY (codigo_produto) REFERENCES produto(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/




