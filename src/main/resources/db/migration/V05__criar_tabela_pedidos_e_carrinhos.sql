CREATE TABLE carrinho (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	valor_total_produtos DECIMAL(10,2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE produto
 ADD COLUMN codigo_carrinho BIGINT(20)
 ;
 
 ALTER TABLE produto
 ADD CONSTRAINT FK_carrinho_produto FOREIGN KEY (codigo_carrinho) REFERENCES carrinho(codigo)
 ;

 CREATE TABLE pedido(
codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
valor_total_compra REAL NOT NULL,
forma_de_pagamento VARCHAR(15),
codigo_carrinho BIGINT(20) NOT NULL,
data_compra DATE,
CONSTRAINT FK_pedido_carrinho FOREIGN KEY (codigo_carrinho) REFERENCES carrinho(codigo)
);

 
INSERT INTO permissao (codigo, descricao) values (9, 'ROLE_GUARDAR_CARRINHO');
INSERT INTO permissao (codigo, descricao) values (10, 'ROLE_REMOVER_CARRINHO');
INSERT INTO permissao (codigo, descricao) values (11, 'ROLE_PESQUISAR_CARRINHO');
INSERT INTO permissao (codigo, descricao) values (12, 'ROLE_ATUALIZAR_CARRINHO');

INSERT INTO permissao (codigo, descricao) values (13, 'ROLE_GRAVAR_PEDIDO');
INSERT INTO permissao (codigo, descricao) values (14, 'ROLE_PESQUISAR_PEDIDO');

INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 9);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 10);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 11);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 12);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 13);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 14);