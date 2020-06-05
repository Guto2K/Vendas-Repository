CREATE TABLE produto (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(30) NOT NULL,
	preco DECIMAL(10,2) NOT NULL,
	img VARCHAR(100) NOT NULL,
	qtda BIGINT(20),
	codigo_categoria BIGINT(20) NOT NULL,
	codigo_pessoa BIGINT(20) NOT NULL,
	FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
) ENGINE= InnoDB DEFAULT CHARSET=utf8;



INSERT INTO produto (nome, preco, img, qtda, codigo_categoria, codigo_pessoa) values ('Mouse', 100.00, 'mouseD.jpg', 150, 7, 1);
INSERT INTO produto (nome, preco, img, qtda, codigo_categoria, codigo_pessoa) values ('Liquitificador', 69.99, 'Liqui.jpg', 300, 1, 2);
INSERT INTO produto (nome, preco, img, qtda, codigo_categoria, codigo_pessoa) values ('Armario', 250.00, 'FortBeg.jpg', 10, 2, 3);
INSERT INTO produto (nome, preco, img, qtda, codigo_categoria, codigo_pessoa) values ('Carrinho', 120.00, 'Happys.jpg', 30, 3, 4);
INSERT INTO produto (nome, preco, img, qtda, codigo_categoria, codigo_pessoa) values ('Sofá', 499.90, 'KingSize.jpg', 15, 4, 5);
