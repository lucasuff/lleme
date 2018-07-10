
INSERT INTO estado(estado_sigla,nome) VALUES('RJ','Rio de Janeiro');
INSERT INTO estado(estado_sigla,nome) VALUES('SP','São Paulo');
INSERT INTO estado(estado_sigla,nome) VALUES('MG','Minas Gerais');

INSERT INTO cidade (estado_sigla, nome) VALUES('RJ','Rio de Janeiro');
INSERT INTO cidade (estado_sigla, nome) VALUES('RJ','Niterói');

SELECT * FROM cidade;

INSERT INTO bairro (cidade_id,nome) VALUES (1,'Méier');
INSERT INTO bairro (cidade_id,nome) VALUES (1,'Taquara');
INSERT INTO bairro (cidade_id,nome) VALUES (1,'Del Castilho');
INSERT INTO bairro (cidade_id,nome) VALUES (1,'Grajaú');
INSERT INTO bairro (cidade_id,nome) VALUES (1,'Centro');
INSERT INTO bairro (cidade_id,nome) VALUES (1,'Ilha do Governador');

INSERT INTO bairro (cidade_id,nome) VALUES (2,'Boa Viagem');
INSERT INTO bairro (cidade_id,nome) VALUES (2,'Ingá');
INSERT INTO bairro (cidade_id,nome) VALUES (2,'São Domingos');
INSERT INTO bairro (cidade_id,nome) VALUES (2,'Centro');
INSERT INTO bairro (cidade_id,nome) VALUES (2,'Icaraí');

SELECT bairro.nome, cidade.nome FROM bairro INNER JOIN cidade on cidade.cidade_id = bairro.cidade_id;
SELECT * FROM bairro;

INSERT INTO faculdade (nome, sigla) VALUES ('Universidade Federal Fluminense','UFF');
INSERT INTO faculdade (nome, sigla) VALUES ('Universidade Federal do Rio de Janeiro','UFRJ');

SELECT * FROM faculdade;

INSERT INTO campus (faculdade_id,bairro_id,nome) VALUES (1,10,'Praia Vermelha'),(1,13,'Valonguinho'),(1,12,'Gragoatá');
INSERT INTO campus (faculdade_id,bairro_id,nome) VALUES (2,17,'Praia Vermelha');

SELECT * FROM campus;

INSERT INTO usuario (nome,whatsapp) VALUES ('José Mauricio','21991346707');
INSERT INTO usuario (nome,whatsapp,faculdade_id) VALUES ('Motorista','21991346708',1);
INSERT INTO usuario (nome,whatsapp,faculdade_id) VALUES ('Passageiro','21991346708',1);
INSERT INTO usuario (nome,whatsapp,faculdade_id) VALUES ('Outra Faculdade','21991369595',2);

SELECT * FROM usuario;

INSERT INTO carro (placa,modelo,cor,usuario_id) VALUES ('KKK0000','Fiat Palio','Preto',2);
INSERT INTO carro (placa,modelo,cor,usuario_id) VALUES ('KKK0001','Fiat Palio','Preto',1);

SELECT * FROM carro;

-- FACULDADE ERRADA
INSERT INTO carona (usuario_id,
					hora_saida,
					carona_status,
					valor,
					vagas_qtd,
					campus_id,
					bairro_id,
					carro_id,
					indo_para_faculdade) VALUES 
					(2,'2018-06-23 15:00:00','ABERTA',600,4,4,1,1,true);
-- CARRO ERRADO
INSERT INTO carona (usuario_id,
					hora_saida,
					carona_status,
					valor,
					vagas_qtd,
					campus_id,
					bairro_id,
					carro_id,
					indo_para_faculdade) VALUES 
					(2,'2018-06-23 15:00:00','ABERTA',600,4,1,1,3,true);
-- TUDO CERTO
INSERT INTO carona (usuario_id,
					hora_saida,
					carona_status,
					valor,
					vagas_qtd,
					campus_id,
					bairro_id,
					carro_id,
					indo_para_faculdade) VALUES 
					(2,'2018-06-23 15:00:00','ABERTA',600,2,1,1,1,true);

					
SELECT * FROM carona;

-- INTERESSE NA PRÓPRIA CARONA 
INSERT INTO carona_interesse (usuario_id,carona_id) VALUES (2,8);

SELECT * FROM carona_interesse;