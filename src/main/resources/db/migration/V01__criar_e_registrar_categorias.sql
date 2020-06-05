CREATE TABLE categoria (

codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL

)ENGINE = InnoDB DEFAULT CHARSET=utf8;



INSERT INTO categoria (nome) values ('Eletronicos Cozinha');
INSERT INTO categoria (nome) values ('Moveis Cozinha');
INSERT INTO categoria (nome) values ('Enxoval');
INSERT INTO categoria (nome) values ('Moveis Sala');
INSERT INTO categoria (nome) values ('Banheiro');
INSERT INTO categoria (nome) values ('Hardware');
