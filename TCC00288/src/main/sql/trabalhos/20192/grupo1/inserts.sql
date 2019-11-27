INSERT INTO usuario (nome,email,senha,celular,ultimo_login,logado,data_nascimento) VALUES ('Iago','iago@uff.br',md5('123'),'9999-0000',now(),1,now());
INSERT INTO usuario (nome,email,senha,celular,ultimo_login,logado,data_nascimento) VALUES ('Mauricio','mauricio@uff.br',md5('123'),'9999-0000',now(),1,now());
INSERT INTO usuario (nome,email,senha,celular,ultimo_login,logado,data_nascimento) VALUES ('Jorge','jorge@uff.br',md5('123'),'9999-0000',now(),1,now());
INSERT INTO usuario (nome,email,senha,celular,ultimo_login,logado,data_nascimento) VALUES ('Diego','diego@uff.br',md5('123'),'9999-0000',now(),1,now());
INSERT INTO usuario (nome,email,senha,celular,ultimo_login,logado,data_nascimento) VALUES ('Super Admin','super@uff.br',md5('123'),'9999-0000',now(),1,now());

INSERT INTO motorista (id_usuario,cpf) VALUES (1,'000.000.000-00');
INSERT INTO motorista (id_usuario,cpf) VALUES (2,'000.000.000-00');
INSERT INTO passageiro (id_usuario,cpf) VALUES (3,'000.000.000-00');
INSERT INTO passageiro (id_usuario,cpf) VALUES (4,'000.000.000-00');

INSERT INTO tarifa (minuto,bandeirada,km,valor_minimo) VALUES (0.4,3.0,4.75,5.70);
INSERT INTO tarifa (minuto,bandeirada,km,valor_minimo) VALUES (0.5,3.0,4.8,4.00);
INSERT INTO tarifa (minuto,bandeirada,km,valor_minimo) VALUES (0.6,3.0,5.9,5.90);
INSERT INTO tarifa (minuto,bandeirada,km,valor_minimo) VALUES (0.7,3.0,4.99,5.00);

INSERT INTO categoria (nome,id_tarifa) VALUES ('Default',1);
INSERT INTO categoria (nome,id_tarifa) VALUES ('X',2);
INSERT INTO categoria (nome,id_tarifa) VALUES ('Black',3);
INSERT INTO categoria (nome,id_tarifa) VALUES ('Juntos',4);

INSERT INTO posicao (id_motorista,lat,lng,data_hora) VALUES (1,-22.908882, -43.202824,NOW());
INSERT INTO posicao (id_motorista,lat,lng,data_hora) VALUES (2,-22.897870, -43.216104,NOW());

INSERT INTO veiculo (placa,id_motorista,cor,categoria_id,marca,modelo) VALUES ('LLL-000',1,'Branco',2,'Toyota','Corola');
INSERT INTO veiculo (placa,id_motorista,cor,categoria_id,marca,modelo) VALUES ('HHH-000',2,'Preto',3,'Toyota','Corola');

INSERT INTO avaliacao_motorista(id_motorista,id_passageiro,nota,mensagem) VALUES (1,1,5,'Muito Bom');
INSERT INTO avaliacao_motorista(id_motorista,id_passageiro,nota,mensagem) VALUES (1,2,3,'Deixou a Desejar');

INSERT INTO avaliacao_motorista(id_motorista,id_passageiro,nota,mensagem) VALUES (2,1,5,'Muito Bom');
INSERT INTO avaliacao_motorista(id_motorista,id_passageiro,nota,mensagem) VALUES (1,1,5,'Sempre Muito Bom');


INSERT INTO avaliacao_passageiro(id_motorista,id_passageiro,nota,mensagem) VALUES (1,1,5,'Passageiro muito gente boa');
INSERT INTO avaliacao_passageiro(id_motorista,id_passageiro,nota,mensagem) VALUES (1,2,3,'Passageiro Atrasou');
INSERT INTO avaliacao_passageiro(id_motorista,id_passageiro,nota,mensagem) VALUES (2,1,2,'Passageiro Atrasou');
INSERT INTO avaliacao_passageiro(id_motorista,id_passageiro,nota,mensagem) VALUES (2,1,3,'Passageiro Atrasou');


INSERT INTO cartao (id_passageiro,numero,cvv,data_expiracao,cpf_cartao) VALUES (1,md5('3234043003090'),'123','2021-01-01','000.000.000-00');

INSERT INTO pais (nome) VALUES ('Brasil');
INSERT INTO pais (nome) VALUES ('Argentina');
INSERT INTO pais (nome) VALUES ('Uruguai');

INSERT INTO estado (id_pais,nome,sigla,timezone) VALUES (1,'Rio de Janeiro','RJ','GMT-3');
INSERT INTO estado (id_pais,nome,sigla,timezone) VALUES (1,'São Paulo','SP','GMT-3');
INSERT INTO estado (id_pais,nome,sigla,timezone) VALUES (1,'Paraiba','PB','GMT-3');
INSERT INTO estado (id_pais,nome,sigla,timezone) VALUES (2,'Patagonia','PT','GMT-2');
INSERT INTO estado (id_pais,nome,sigla,timezone) VALUES (2,'Central','CT','GMT-2');
INSERT INTO estado (id_pais,nome,sigla,timezone) VALUES (3,'Maldonado','MD','GMT-2');

INSERT INTO cidade (id_estado,nome) VALUES (1,'Cabo Frio');
INSERT INTO cidade (id_estado,nome) VALUES (1,'Niteroi');
INSERT INTO cidade (id_estado,nome) VALUES (2,'Santos');
INSERT INTO cidade (id_estado,nome) VALUES (3,'Juiz de Fora');
INSERT INTO cidade (id_estado,nome) VALUES (5,'Buenos Aires');
INSERT INTO cidade (id_estado,nome) VALUES (6,'Punta del Este');

INSERT INTO corrida (minutos,id_tarifa,valor,id_cidade,data_hora,estimativa_km,estimativa_minutos,estimativa_valor,id_motorista,km,id_passageiro) VALUES (10,2,30.0,1,NOW(),10,10,28.0,1,9,1);
INSERT INTO corrida (minutos,id_tarifa,valor,id_cidade,data_hora,estimativa_km,estimativa_minutos,estimativa_valor,id_motorista,km,id_passageiro) VALUES (10,2,30.0,2,NOW(),10,10,28.0,1,9,2);
INSERT INTO corrida (minutos,id_tarifa,valor,id_cidade,data_hora,estimativa_km,estimativa_minutos,estimativa_valor,id_motorista,km,id_passageiro) VALUES (10,2,30.0,2,NOW(),10,10,28.0,2,9,1);
INSERT INTO corrida (minutos,id_tarifa,valor,id_cidade,data_hora,estimativa_km,estimativa_minutos,estimativa_valor,id_motorista,km,id_passageiro) VALUES (10,2,30.0,2,NOW(),10,10,28.0,2,9,2);

--- PAROU AQUI----

INSERT INTO transacao (id_corrida,valor) VALUES (1,20.0);
INSERT INTO transacao (id_corrida,valor) VALUES (2,40.0);
INSERT INTO transacao (id_corrida,valor) VALUES (3,10.0);
INSERT INTO transacao (id_corrida,valor) VALUES (4,5.0);

INSERT INTO conta (id_motorista,id_transacao,valor,data_limite) VALUES (1,1,10.0,'2019-12-30');
INSERT INTO conta (id_motorista,id_transacao,valor,data_limite) VALUES (1,2,10.0,'2019-12-30');
INSERT INTO conta (id_motorista,id_transacao,valor,data_limite) VALUES (2,3,10.0,'2019-12-30');


INSERT INTO transacao_cartao (id_transacao,id_cartao) VALUES (1,1);
INSERT INTO transacao_cartao (id_transacao,id_cartao) VALUES (2,1);

INSERT INTO transacao_dinheiro (id_transacao) VALUES (3);


INSERT INTO cupom ("código",desconto, quantidade,primeira_corrida) VALUES ('FIRST',10.0,1,1);

INSERT INTO gestor (id_usuario) VALUES (5);

INSERT INTO endereço_favorito (id_passageiro,lat,lng,endereco,bairro) VALUES (17,-22.905105, -43.176373,'Rua da Assembleia 10','Centro');
INSERT INTO endereço_favorito (id_passageiro,lat,lng,endereco,bairro) VALUES (17,-22.904433, -43.177896,'Avenida Rio Branco 102','Centro');

INSERT INTO notificacao (id_gestor,id_motorista,mensagem,data_hora) VALUES (1,1,'BEM VINDO MOTORISTA',NOW());
INSERT INTO notificacao (id_gestor,id_motorista,mensagem,data_hora) VALUES (1,2,'BEM VINDO MOTORISTA',NOW());
INSERT INTO notificacao (id_gestor,id_passageiro,mensagem,data_hora) VALUES (1,1,'BEM VINDO PASSAGEIRO',NOW());
INSERT INTO notificacao (id_gestor,id_passageiro,mensagem,data_hora) VALUES (1,2,'BEM VINDO PASSAGEIRO',NOW());
