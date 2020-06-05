CREATE TABLE pessoa (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	rua VARCHAR(30),
	numero VARCHAR(30),
	complemento VARCHAR(30),
	bairro VARCHAR(30),
	cep VARCHAR(30),
	cidade VARCHAR(30),
	estado VARCHAR(30),
	ativo BOOLEAN NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, rua, numero, complemento, bairro, cep, cidade, estado, ativo) values ('João Silva', 'Rua do Abacaxi', '10', null,'Laranjinha', '038.40-412', 'Campinas', 'SP', true);
INSERT INTO pessoa (nome, rua, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Rodolfo Ladri', 'Rua do Laranja', '59', null, 'Acerolinha', '038.40-892', 'Uberlândia', 'MG', true);
INSERT INTO pessoa (nome, rua, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Rafael Couto', 'Rua do Banana', '13', '2B', null , '038.68-482', 'Juscelio', 'RJ', true);
INSERT INTO pessoa (nome, rua, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Paulo Castro', 'Rua do Pera', '1006', null, null , '034.80-222', 'Meriva', 'RS', true);
INSERT INTO pessoa (nome, rua, numero, complemento, bairro, cep, cidade, estado, ativo) values ('Rodrigo Silva', 'Rua do Morango', '568', '4A', 'Macaquinho', '089.44-414', 'Lagoinha', 'BA', true);

