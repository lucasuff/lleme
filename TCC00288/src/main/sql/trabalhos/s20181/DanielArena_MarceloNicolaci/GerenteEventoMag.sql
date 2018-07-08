DROP TABLE IF EXISTS bane;
DROP TABLE IF EXISTS colore;
DROP TABLE IF EXISTS participa;
DROP TABLE IF EXISTS pertence;
DROP TABLE IF EXISTS decks;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS eventos; 
DROP TABLE IF EXISTS formatos;
DROP TABLE IF EXISTS cartas;
DROP TABLE IF EXISTS artistas;
DROP TABLE IF EXISTS colecoes;
DROP TABLE IF EXISTS tipos_cartas;
DROP TABLE IF EXISTS cores;


CREATE TABLE usuarios
(
    nome text NOT NULL,
    cpf text NOT NULL,
    CONSTRAINT usuarios_pkey PRIMARY KEY (cpf)
);

CREATE TABLE formatos
(
    id integer NOT NULL,
    nome text NOT NULL,
    minimo integer NOT NULL,
	tem_comandante bool NOT NULL,
    maximo integer,
	qtd_repetidas integer,
    CONSTRAINT formatos_pkey PRIMARY KEY (id)
);

CREATE TABLE decks
(
    id integer NOT NULL,
    usuario_cpf text NOT NULL,
    formato_id integer NOT NULL,
    nome text,
    CONSTRAINT decks_pkey PRIMARY KEY (id),
    CONSTRAINT decks_formato_id_fkey FOREIGN KEY (formato_id)
        REFERENCES formatos (id),
    CONSTRAINT decks_usuario_cpf_fkey FOREIGN KEY (usuario_cpf)
        REFERENCES usuarios (cpf)
);

CREATE TABLE eventos
(
	id integer NOT NULL,
	nome text NOT NULL,
	limite integer NOT NULL,
	formato_id integer NOT NULL,
	participantes integer NOT NULL DEFAULT 0,
	CONSTRAINT eventos_id PRIMARY KEY (id),
	CONSTRAINT formatos_id_fkey FOREIGN KEY (formato_id)
		REFERENCES formatos (id)
);

CREATE TABLE artistas
(
	id integer NOT NULL,
	nome text NOT NULL,
	CONSTRAINT artista_id PRIMARY KEY (id)
);

CREATE TABLE colecoes
(
	id integer NOT NULL,
	nome text NOT NULL,
	CONSTRAINT colecao_id PRIMARY KEY (id)
);

CREATE TABLE tipos_cartas
(
	id integer NOT NULL,
	tipo text NOT NULL,
	basic_land bool NOT NULL,
	sub_tipo text,
	super_tipo text,
	CONSTRAINT tipo_carta_id PRIMARY KEY (id)
);

CREATE TABLE cartas
(
	id integer NOT NULL,
	nome text NOT NULL,
	tipo_id integer NOT NULL,
	raridade text NOT NULL,
	texto text,
	artista_id integer NOT NULL,
	colecao_id integer NOT NULL,
	CONSTRAINT cartas_id PRIMARY KEY (id),
	CONSTRAINT tipo_id_fkey FOREIGN KEY (tipo_id)
		REFERENCES tipos_cartas (id),
	CONSTRAINT artista_id_fkey FOREIGN KEY (artista_id)
		REFERENCES artistas (id),
	CONSTRAINT colecao_id_fkey  FOREIGN KEY (colecao_id)
		REFERENCES colecoes (id)
);

CREATE TABLE cores
(
	id integer NOT NULL,
	cor text NOT NULL,
	incolor bool NOT NULL,
	CONSTRAINT cor_id PRIMARY KEY (id)
);

CREATE TABLE participa
(
	deck_id integer NOT NULL,
	evento_id integer NOT NULL,
	CONSTRAINT participa_id PRIMARY KEY (deck_id, evento_id),
	CONSTRAINT deck_id_fkey FOREIGN KEY (deck_id)
		REFERENCES decks (id),
	CONSTRAINT evento_id_Fkey FOREIGN KEY (evento_id)
		REFERENCES eventos (id)
);

CREATE TABLE pertence
(
	deck_id integer NOT NULL,
	carta_id integer NOT NULL,
	quantidade integer NOT NULL,
	is_comandante bool NOT NULL,
	CONSTRAINT pertence_id PRIMARY KEY (deck_id, carta_id),
	CONSTRAINT deck_id_fkey FOREIGN KEY (deck_id)
		REFERENCES decks (id),
	CONSTRAINT carta_id_fkey FOREIGN KEY (carta_id)
		REFERENCES cartas (id)
);

CREATE TABLE colore
(
	carta_id integer NOT NULL,
	cor_id integer NOT NULL,
	CONSTRAINT colore_id PRIMARY KEY (carta_id, cor_id),
	CONSTRAINT carta_id_fkey FOREIGN KEY (carta_id)
		REFERENCES cartas (id),
	CONSTRAINT cor_id_fkey FOREIGN KEY (cor_id)
		REFERENCES cores (id)
);

CREATE TABLE bane
(
	carta_id integer NOT NULL,
	formato_id integer NOT NULL,
	CONSTRAINT bane_id PRIMARY KEY (carta_id, formato_id),
	CONSTRAINT carta_id_fkey FOREIGN KEY (carta_id)
		REFERENCES cartas (id),
	CONSTRAINT formato_id_fkey FOREIGN KEY (formato_id)
		REFERENCES formatos (id)
);

CREATE OR REPLACE FUNCTION checa_formato_evento() RETURNS trigger AS $$
DECLARE
	formato_ev integer;
	formato_dk integer;
	limi integer;
	participante integer;
	qtd integer;
	mini integer;
BEGIN
	SELECT formato_id, limite, participantes INTO formato_ev, limi, participante
		FROM eventos
		WHERE eventos.id = NEW.evento_id;
	SELECT formato_id INTO formato_dk
		FROM decks
		WHERE decks.id = NEW.deck_id;
	IF formato_ev != formato_dk THEN
		RAISE EXCEPTION 'formatos invalido % %', formato_ev, formato_dk;
	END IF;
	IF limi = participante THEN
		RAISE EXCEPTION 'limite excedido %', limi;
	END IF;
	
	SELECT sum(quantidade) INTO qtd
		from pertence
		where deck_id = new.deck_id;
	SELECT minimo AS mi INTO mini
		from formatos
		where id = formato_ev;
	IF (qtd < mini) OR qtd is null then
		RAISE EXCEPTION 'minimo de cartas nao respeitado %/%', qtd, mini;
	END IF;
	RETURN NEW;	
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER insere_participa_trigger BEFORE INSERT OR UPDATE ON participa
FOR EACH ROW EXECUTE PROCEDURE checa_formato_evento();

CREATE OR REPLACE FUNCTION incrementa_participantes() RETURNS trigger AS $$
DECLARE
BEGIN
	IF TG_OP != 'INSERT' THEN
		UPDATE eventos
		SET participantes = (SELECT count(*) FROM participa WHERE evento_id = OLD.evento_id)
		WHERE id = OLD.evento_id;
	END IF;
	IF TG_OP != 'DELETE' THEN
		UPDATE eventos
		SET participantes = (SELECT count(*) FROM participa WHERE evento_id = NEW.evento_id)
		WHERE id = NEW.evento_id;	
	END IF;
	IF TG_OP = 'DELETE' THEN
		RETURN OLD;
	END IF;
	RETURN NEW;	
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER incrementa_participantes_trigger AFTER INSERT OR UPDATE OR DELETE ON participa
FOR EACH ROW EXECUTE PROCEDURE incrementa_participantes();

CREATE OR REPLACE FUNCTION muda_evento() RETURNS trigger AS $$
DECLARE
	_participante integer;
BEGIN
	if TG_OP = 'UPDATE' then
		if OLD.participantes != 0 and old.formato_id != new.formato_id then
			RAISE EXCEPTION 'participante ja inscritos';
		end if;
	else
		if OLD.participantes != 0 then
			RAISE EXCEPTION 'participante ja inscritos';
		end if;
	end if;
	return new;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER muda_evento_trigger BEFORE UPDATE OR DELETE ON eventos
FOR EACH ROW EXECUTE PROCEDURE muda_evento();


CREATE OR REPLACE FUNCTION muda_deck() RETURNS trigger AS $$
DECLARE
BEGIN
	if EXISTS (SELECT * FROM participa WHERE participa.deck_id = old.id) then
		RAISE EXCEPTION 'Deck participa de algum evento.';
	end if;
	return new;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER muda_deck_trigger BEFORE UPDATE OR DELETE ON decks
FOR EACH ROW EXECUTE PROCEDURE muda_deck();

CREATE OR REPLACE FUNCTION muda_formato() RETURNS trigger AS $$
DECLARE
BEGIN
	if EXISTS (SELECT * FROM decks WHERE decks.formato_id = old.id) then
		RAISE EXCEPTION 'Formato não pode ser alterado se algum deck usa ele.';
	end if;
	return new;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER muda_formato_trigger BEFORE UPDATE OR DELETE ON formatos
FOR EACH ROW EXECUTE PROCEDURE muda_formato();


CREATE OR REPLACE FUNCTION muda_banida() RETURNS trigger AS $$
DECLARE
BEGIN
	if EXISTS (SELECT * FROM pertence INNER JOIN decks on (pertence.deck_id = decks.id) 
			   WHERE pertence.carta_id = new.carta_id and decks.formato_id = new.formato_id) then		   
		RAISE EXCEPTION 'Carta a ser banida pertence a um deck.';
	end if;
	return new;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER muda_banida_trigger BEFORE UPDATE OR INSERT ON bane
FOR EACH ROW EXECUTE PROCEDURE muda_banida();

CREATE OR REPLACE FUNCTION muda_formato_deck() RETURNS trigger AS $$
DECLARE
BEGIN
	if EXISTS (SELECT * FROM (SELECT * FROM pertence WHERE deck_id = old.id) AS cards INNER JOIN bane on (cards.carta_id = bane.carta_id) 
			   WHERE bane.formato_id = new.formato_id ) or new.id <> old.id then		   
		RAISE EXCEPTION 'O deck tem alguma carta banida no novo formato';
	end if;
		if EXISTS (SELECT * FROM pertence WHERE deck_id = old.id and quantidade <= (SELECT qtd_repetidas FROM formatos WHERE id = new.formato_id)) then
			RAISE EXCEPTION 'Alguma carta não respeita o limite de quantidade';
	end if;
	return new;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER muda_formato_deck_trigger BEFORE UPDATE ON decks
FOR EACH ROW EXECUTE PROCEDURE muda_formato_deck();

CREATE OR REPLACE FUNCTION muda_tipo_carta() RETURNS trigger AS $$
DECLARE
BEGIN
	if EXISTS (SELECT * FROM cartas WHERE cartas.tipo_id = old.id) then
		RAISE EXCEPTION 'Alguma carta já usa tipo.';
	end if;
	return new;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER muda_tipo_carta_trigger BEFORE UPDATE OR DELETE ON tipos_cartas
FOR EACH ROW EXECUTE PROCEDURE muda_tipo_carta();

CREATE OR REPLACE FUNCTION checa_validade_carta() RETURNS trigger AS $$
DECLARE
	qtd integer;
	formato integer;
	banida bool;
	land bool;
	maxi integer;
	qtd2 integer;
BEGIN
	SELECT formato_id into formato
				FROM decks
				where id = new.deck_id;
	SELECT basic_land INTO land
		from tipos_cartas
		inner join cartas on tipos_cartas.id = cartas.tipo_id
		where cartas.id = new.carta_id;
	SELECT qtd_repetidas, maximo INTO qtd, maxi
		from formatos
		where id = formato;
	if new.quantidade > qtd and not land then
		RAISE EXCEPTION 'limite excedido %', qtd;
	end if;
	IF EXISTS (SELECT carta_id FROM BANE WHERE formato_id = formato AND carta_id = new.carta_id) THEN
		RAISE EXCEPTION 'Carta banida no formato do deck';
	END IF;
	IF maxi is not null then
		SELECT sum(quantidade) INTO qtd2
		from pertence
		where deck_id = new.deck_id;
		if (qtd2 + new.quantidade) > maxi then
			RAISE EXCEPTION 'limite excedido %/%', (qtd2+new.quantidade), maxi;
		end if;
	end if;
	return new;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER checa_validade_carta_trigger BEFORE INSERT OR UPDATE ON pertence
FOR EACH ROW EXECUTE PROCEDURE checa_validade_carta();

CREATE OR REPLACE FUNCTION checa_commander_carta() RETURNS trigger AS $$
DECLARE
	commander bool;
	tem_commander integer;
BEGIN
	SELECT tem_comandante INTO commander
		from formatos
		where id = (SELECT formato_id FROM decks where id = new.deck_id);
	if commander then
		SELECT carta_id INTO tem_commander FROM pertence WHERE deck_id = new.deck_id AND is_comandante = true;
		IF new.is_comandante and tem_commander is not null THEN
			RAISE EXCEPTION 'Comandante ja comandando';
		ELSIF tem_commander is not null THEN
			IF (EXISTS ((select cor_id from colore where carta_id = new.carta_id) EXCEPT (select cor_id from colore where carta_id =tem_commander))) 
				and not EXISTS (SELECT cor_id from colore inner join cores on (cor_id = cores.id) where carta_id = new.carta_id and cores.incolor = true) 
				then
				RAISE EXCEPTION 'Cor incompativel com o comandante';
			end if;
		ELSIF not new.is_comandante then
			RAISE EXCEPTION 'Esperando comandante';
		END IF;	
	END IF;
	return new;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER checa_commander_carta_trigger BEFORE INSERT OR UPDATE ON pertence
FOR EACH ROW EXECUTE PROCEDURE checa_commander_carta();


CREATE OR REPLACE FUNCTION checa_cor_carta() RETURNS trigger AS $$
DECLARE
	incolor integer[];
	cores integer[];
BEGIN
	incolor := (SELECT array_agg(cor_id) 
 			   from colore inner join cores on (cor_id = cores.id) 
 			   where carta_id = new.carta_id and cores.incolor = true);
	cores := (SELECT array_agg(cor_id)  
 			   from colore inner join cores on (cor_id = cores.id) 
 			   where carta_id = new.carta_id and cores.incolor = false);
	IF array_length(incolor, 1) > 0 and array_length(cores, 1) > 0 THEN
		RAISE EXCEPTION 'Carta nao pode ser incolor e ter cor ao mesmo tempo';
	END IF;
	IF EXISTS (SELECT cor_id 
 			from colore inner join cores on (cor_id = cores.id) 
 			where carta_id = new.carta_id and cores.incolor = true) AND
			EXISTS (SELECT id 
 			from cores
 			where id = new.cor_id and cores.incolor = false)
			THEN
     	   	RAISE EXCEPTION 'Carta já é incolor';
	END IF;
	IF EXISTS (SELECT cor_id 
 			from colore inner join cores on (cor_id = cores.id) 
 			where carta_id = new.carta_id and cores.incolor = false) AND
			EXISTS (SELECT id 
 			from cores
 			where id = new.cor_id and cores.incolor = true)
			THEN
     	   	RAISE EXCEPTION 'Carta já tem cor';
	END IF;
	return new;
END; $$ LANGUAGE plpgsql;

CREATE TRIGGER checa_cor_carta_trigger BEFORE INSERT OR UPDATE ON colore
FOR EACH ROW EXECUTE PROCEDURE checa_cor_carta();

DROP FUNCTION IF EXISTS  cartas_mais_jogadas(integer,integer);
CREATE OR REPLACE FUNCTION cartas_mais_jogadas(_evento integer, n_ranking integer) 
RETURNS TABLE( carta integer,
			   aparicoes bigint
			  ) AS $$
DECLARE
BEGIN
   return QUERY  SELECT carta_id, sum(quantidade) as aparicoes
		   from participa inner join pertence on (participa.deck_id = pertence.deck_id and participa.evento_id = _evento) 
           where carta_id != (select cartas.id
							 from cartas inner join tipos_cartas on (cartas.tipo_id = tipos_cartas.id)
							 where basic_land = true
							 )
		   GROUP BY carta_id
		   ORDER BY aparicoes DESC 
		   LIMIT n_ranking;
END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION insere_carta(_nome text, _tipo_id integer, _raridade text, _texto text, _artista_id integer, _colecao_id integer, VARIADIC cores integer[]) RETURNS void AS $$
DECLARE
	_id integer;
	_cor_id integer;
BEGIN
	_id := (SELECT coalesce(max(id), 0) + 1 FROM cartas);
	INSERT INTO cartas (id, nome, tipo_id, raridade, texto, artista_id, colecao_id)
	VALUES (_id, _nome, _tipo_id, _raridade, _texto, _artista_id, _colecao_id);
	foreach _cor_id in array cores loop
		INSERT INTO colore (carta_id, cor_id) VALUES (_id, _cor_id);
	end loop;
END; $$ LANGUAGE plpgsql;

-----------------------------------------------------POPULAÇÃO DA TABELA-----------------------------------------------------

--INSERT INTO usuarios VALUES ('nome', 'cpf');
INSERT INTO usuarios VALUES ('Marcelo', '147.095.947-08');
INSERT INTO usuarios VALUES ('Arena', '172.912.657-03');
INSERT INTO usuarios VALUES ('fulano', '147.095.937-28');

--INSERT INTO formatos VALUES (id, 'nome', minimo, tem_commander, maximo, qtd_repetidas);
INSERT INTO formatos VALUES (1, 'commander',100,true,100,1);
INSERT INTO formatos VALUES (2, 'modern',60,false,NULL,4);
INSERT INTO formatos VALUES (3, 'standard',60,false,NULL,4);
INSERT INTO formatos VALUES (4, 'draft',40,false);

--INSERT INTO decks VALUES (id, 'cpf', formato_id, 'nome');
INSERT INTO decks VALUES (1, '147.095.947-08', 2, 'kamigawa');
INSERT INTO decks VALUES (2, '172.912.657-03', 1, 'gruphug');
INSERT INTO decks VALUES (3, '147.095.937-28', 1, 'nan');
INSERT INTO decks VALUES (4, '147.095.937-28', 2, 'Grupdeath');
INSERT INTO decks VALUES (5, '147.095.947-08', 2, 'ModernStomper');
INSERT INTO decks VALUES (6, '147.095.937-28', 1, 'Combo');

--INSERT INTO eventos VALUES (id, 'nome', limite, formato_id, participantes);
INSERT INTO eventos VALUES (1, 'teste', 5, 2);
INSERT INTO eventos VALUES (2, 'testeCom', 10, 1);
INSERT INTO eventos VALUES (3, 'testeLim', 2, 2);

--INSERT INTO artistas VALUES (id, 'nome');
INSERT INTO artistas VALUES (1, 'Tsutomu kawade');

--INSERT INTO colecoes VALUES (id, 'nome');
INSERT INTO colecoes VALUES (1, 'kamigawa');

--INSERT INTO tipos_cartas VALUES id1, 'tipo', basic_land, subTipo, superTipo);
INSERT INTO tipos_cartas VALUES (1, 'criatura', false);
INSERT INTO tipos_cartas VALUES (2, 'land', true, 'floresta');

--INSERT INTO cores VALUES (id,'cor', incolor);
INSERT INTO cores VALUES (1,'incolor', true);
INSERT INTO cores VALUES (2,'vermelho', false);
INSERT INTO cores VALUES (3,'azul', false);
INSERT INTO cores VALUES (4,'preto', false);
INSERT INTO cores VALUES (5,'branco', false);
INSERT INTO cores VALUES (6,'verde', false);

--SELECT insere_carta('nome', tipo_id, 'raridade', texto, artista_id, colecao_id, [id_cor]);
BEGIN;
  SELECT insere_carta('aOUGJci', 1, 'raro', NULL, 1, 1, 2, 6);
  SELECT insere_carta('DBuaqZG', 1, 'incomum', NULL, 1, 1, 2, 4, 6);
  SELECT insere_carta('bwDNVxP', 1, 'mitico-raro', NULL, 1, 1, 5, 4, 2, 3, 6);
  SELECT insere_carta('JhjLQFi', 1, 'comum', NULL, 1, 1, 2);
  SELECT insere_carta('aAGcrqz', 1, 'incomum', NULL, 1, 1, 2, 5, 4);
  SELECT insere_carta('lbdaoYU', 1, 'comum', NULL, 1, 1, 4, 5, 2);
  SELECT insere_carta('krfhkyA', 1, 'comum', NULL, 1, 1, 1);
  SELECT insere_carta('MFxLmuC', 1, 'raro', NULL, 1, 1, 5, 3, 4, 6);
  SELECT insere_carta('RbKgBcz', 1, 'incomum', NULL, 1, 1, 5, 2, 6);
  SELECT insere_carta('DtZtCCF', 1, 'incomum', NULL, 1, 1, 5, 6);
  SELECT insere_carta('ozGlgzq', 1, 'incomum', NULL, 1, 1, 5, 3);
  SELECT insere_carta('IkuBZkS', 1, 'raro', NULL, 1, 1, 2, 5);
  SELECT insere_carta('GtYPDZl', 1, 'incomum', NULL, 1, 1, 1);
  SELECT insere_carta('unBpTgO', 1, 'incomum', NULL, 1, 1, 5, 4);
  SELECT insere_carta('vEmLrZb', 1, 'incomum', NULL, 1, 1, 1);
  SELECT insere_carta('XuXIOkj', 1, 'raro', NULL, 1, 1, 4, 5);
  SELECT insere_carta('YrOWRBM', 1, 'raro', NULL, 1, 1, 2, 5);
  SELECT insere_carta('SKMzqqj', 1, 'raro', NULL, 1, 1, 1);
  SELECT insere_carta('YlmFdIu', 1, 'raro', NULL, 1, 1, 4, 6);
  SELECT insere_carta('XfmjVDP', 1, 'mitico-raro', NULL, 1, 1, 5);
  SELECT insere_carta('SvMAhvU', 1, 'raro', NULL, 1, 1, 4, 5, 2);
  SELECT insere_carta('eEzbIrU', 1, 'incomum', NULL, 1, 1, 2, 5, 4);
  SELECT insere_carta('qsLhhET', 1, 'comum', NULL, 1, 1, 2);
  SELECT insere_carta('zQPnbEF', 1, 'comum', NULL, 1, 1, 5, 2);
  SELECT insere_carta('GmmoWcj', 1, 'raro', NULL, 1, 1, 5, 3);
  SELECT insere_carta('MWlaEnz', 1, 'mitico-raro', NULL, 1, 1, 4, 5, 3);
  SELECT insere_carta('bSahpVy', 1, 'comum', NULL, 1, 1, 1);
  SELECT insere_carta('yvKedtS', 1, 'comum', NULL, 1, 1, 1);
  SELECT insere_carta('JlPIPzy', 1, 'comum', NULL, 1, 1, 2, 3, 4, 6);
  SELECT insere_carta('rbgeEOm', 1, 'mitico-raro', NULL, 1, 1, 4);
  SELECT insere_carta('jgAoLDw', 1, 'incomum', NULL, 1, 1, 2, 3, 6);
  SELECT insere_carta('giwXIyg', 1, 'comum', NULL, 1, 1, 4, 5, 2);
  SELECT insere_carta('kvgTiir', 1, 'raro', NULL, 1, 1, 4, 3, 6);
  SELECT insere_carta('ZjhuNHi', 1, 'mitico-raro', NULL, 1, 1, 2, 5, 3);
  SELECT insere_carta('wNuhlIe', 1, 'incomum', NULL, 1, 1, 2, 5, 3, 6);
  SELECT insere_carta('llOIsKY', 1, 'comum', NULL, 1, 1, 2, 5, 3);
  SELECT insere_carta('MCYlsGN', 1, 'mitico-raro', NULL, 1, 1, 5, 3);
  SELECT insere_carta('zQZvXYN', 1, 'incomum', NULL, 1, 1, 5, 3, 6);
  SELECT insere_carta('AyMItyv', 1, 'incomum', NULL, 1, 1, 1);
  SELECT insere_carta('SvEHbuc', 1, 'comum', NULL, 1, 1, 2);
  SELECT insere_carta('JJDpFul', 1, 'incomum', NULL, 1, 1, 5);
  SELECT insere_carta('GVVMBIq', 1, 'comum', NULL, 1, 1, 4, 5, 3, 6);
  SELECT insere_carta('YaXnuyM', 1, 'incomum', NULL, 1, 1, 4, 5, 6);
  SELECT insere_carta('YNEZnYm', 1, 'mitico-raro', NULL, 1, 1, 4, 5, 3);
  SELECT insere_carta('OpRtxCR', 1, 'raro', NULL, 1, 1, 1);
  SELECT insere_carta('XFWzPlk', 1, 'incomum', NULL, 1, 1, 4, 3);
  SELECT insere_carta('QGnokZo', 1, 'comum', NULL, 1, 1, 5, 3);
  SELECT insere_carta('abOjPii', 1, 'raro', NULL, 1, 1, 2, 3);
  SELECT insere_carta('zLQPVMN', 1, 'mitico-raro', NULL, 1, 1, 2, 3, 4, 6);
  SELECT insere_carta('qdxtpbp', 1, 'comum', NULL, 1, 1, 4, 3);
  SELECT insere_carta('VvXGFDR', 1, 'incomum', NULL, 1, 1, 4, 3, 6);
  SELECT insere_carta('gvEuFoK', 1, 'raro', NULL, 1, 1, 3);
  SELECT insere_carta('YLWiOux', 1, 'comum', NULL, 1, 1, 4, 6);
  SELECT insere_carta('KmXzyvS', 1, 'mitico-raro', NULL, 1, 1, 5, 2, 4);
  SELECT insere_carta('eUWyPaY', 1, 'raro', NULL, 1, 1, 5, 3);
  SELECT insere_carta('SoTNTIy', 1, 'raro', NULL, 1, 1, 4, 6);
  SELECT insere_carta('NNRYydf', 1, 'mitico-raro', NULL, 1, 1, 2, 5, 3, 6);
  SELECT insere_carta('OjkghLl', 1, 'comum', NULL, 1, 1, 4, 6);
  SELECT insere_carta('TQjympa', 1, 'incomum', NULL, 1, 1, 5, 3, 4);
  SELECT insere_carta('IagrBFG', 1, 'raro', NULL, 1, 1, 5, 6);
  SELECT insere_carta('iHTiHWQ', 1, 'raro', NULL, 1, 1, 6);
  SELECT insere_carta('MapObVi', 1, 'comum', NULL, 1, 1, 5, 3, 6);
  SELECT insere_carta('PchAsZb', 1, 'incomum', NULL, 1, 1, 1);
  SELECT insere_carta('tmJqDcp', 1, 'mitico-raro', NULL, 1, 1, 2, 5, 3);
  SELECT insere_carta('WLhGKDD', 1, 'raro', NULL, 1, 1, 1);
  SELECT insere_carta('tuzZdDb', 1, 'mitico-raro', NULL, 1, 1, 5, 3, 6);
  SELECT insere_carta('clzAcER', 1, 'incomum', NULL, 1, 1, 2);
  SELECT insere_carta('TuALtJN', 1, 'raro', NULL, 1, 1, 5, 4);
  SELECT insere_carta('wcPXHEJ', 1, 'mitico-raro', NULL, 1, 1, 6);
  SELECT insere_carta('jXRrNTE', 1, 'comum', NULL, 1, 1, 4, 3, 6);
  SELECT insere_carta('uqtylhx', 1, 'comum', NULL, 1, 1, 4);
  SELECT insere_carta('VKuZfse', 1, 'comum', NULL, 1, 1, 5, 3);
  SELECT insere_carta('lXxhoHs', 1, 'mitico-raro', NULL, 1, 1, 2, 4);
  SELECT insere_carta('JNcRACx', 1, 'mitico-raro', NULL, 1, 1, 2, 6);
  SELECT insere_carta('IfLcnrC', 1, 'raro', NULL, 1, 1, 2, 3, 4, 6);
  SELECT insere_carta('quMjRhs', 1, 'incomum', NULL, 1, 1, 6);
  SELECT insere_carta('NUDLOeJ', 1, 'mitico-raro', NULL, 1, 1, 5, 3, 4);
  SELECT insere_carta('EgDxWiQ', 1, 'comum', NULL, 1, 1, 3, 6);
  SELECT insere_carta('OEPrqhh', 1, 'incomum', NULL, 1, 1, 5, 2);
  SELECT insere_carta('iANPQKc', 1, 'raro', NULL, 1, 1, 1);
  SELECT insere_carta('JvIiFMl', 1, 'incomum', NULL, 1, 1, 4, 3);
  SELECT insere_carta('ReVKtyw', 1, 'mitico-raro', NULL, 1, 1, 3, 6);
  SELECT insere_carta('ZZEUwhY', 1, 'raro', NULL, 1, 1, 5);
  SELECT insere_carta('njAkcpT', 1, 'raro', NULL, 1, 1, 2, 5, 3);
  SELECT insere_carta('HjnQJKD', 1, 'comum', NULL, 1, 1, 4, 2, 6);
  SELECT insere_carta('fjPKaST', 1, 'raro', NULL, 1, 1, 3);
  SELECT insere_carta('HSqqJxE', 1, 'raro', NULL, 1, 1, 3);
  SELECT insere_carta('DsQqzPE', 1, 'raro', NULL, 1, 1, 1);
  SELECT insere_carta('ixiYnAs', 1, 'incomum', NULL, 1, 1, 6);
  SELECT insere_carta('QyEhZpY', 1, 'comum', NULL, 1, 1, 5, 3, 6);
  SELECT insere_carta('bEVMzhD', 1, 'raro', NULL, 1, 1, 5, 4);
  SELECT insere_carta('VKIpcmW', 1, 'raro', NULL, 1, 1, 2);
  SELECT insere_carta('BleRoiA', 1, 'raro', NULL, 1, 1, 5, 2);
  SELECT insere_carta('rfXRxtR', 1, 'raro', NULL, 1, 1, 5, 3, 4, 6);
  SELECT insere_carta('hyRAqQM', 1, 'raro', NULL, 1, 1, 5, 3);
  SELECT insere_carta('XRgvJCI', 1, 'mitico-raro', NULL, 1, 1, 2);
  SELECT insere_carta('eECpDGu', 1, 'comum', NULL, 1, 1, 6);
  SELECT insere_carta('iRkiZwC', 1, 'incomum', NULL, 1, 1, 2, 3, 6);
  SELECT insere_carta('xpfCUGs', 1, 'incomum', NULL, 1, 1, 3);
  SELECT insere_carta('LuMfBaK', 1, 'comum', NULL, 1, 1, 2, 3);
END;
SELECT insere_carta('Pupudim', 2, 'comum', NULL, 1, 1, 2, 6);

--INSERT INTO bane VALUES (carta_id, formato_id);
INSERT INTO bane VALUES (1, 2);
INSERT INTO bane VALUES (2, 2);
INSERT INTO bane VALUES (3, 2);
INSERT INTO bane VALUES (4, 2);
INSERT INTO bane VALUES (9, 2);

--INSERT INTO pertence VALUES (deck, carta, quantidade, is_comandante;
INSERT INTO pertence VALUES (1, 5, 1,false);
INSERT INTO pertence VALUES (1, 101, 30,false);
INSERT INTO pertence VALUES (1, 100, 4,false);
INSERT INTO pertence VALUES (1, 99, 4,false);
INSERT INTO pertence VALUES (1, 98, 4,false);
INSERT INTO pertence VALUES (1, 97, 4,false);
INSERT INTO pertence VALUES (1, 96, 4,false);
INSERT INTO pertence VALUES (1, 95, 4,false);
INSERT INTO pertence VALUES (1, 94, 4,false);
INSERT INTO pertence VALUES (1, 93, 4,false);
INSERT INTO pertence VALUES (2, 9, 1,true);
INSERT INTO pertence VALUES (2, 15, 1,false);
INSERT INTO pertence VALUES (2, 101, 97,false);
INSERT INTO pertence VALUES (3, 9, 1,true);
INSERT INTO pertence VALUES (3, 15, 1,false);
INSERT INTO pertence VALUES (3, 101, 50,false);
INSERT INTO pertence VALUES (4, 5, 1,false);
INSERT INTO pertence VALUES (4, 101, 30,false);
INSERT INTO pertence VALUES (4, 100, 4,false);
INSERT INTO pertence VALUES (4, 99, 4,false);
INSERT INTO pertence VALUES (4, 98, 4,false);
INSERT INTO pertence VALUES (4, 35, 4,false);
INSERT INTO pertence VALUES (4, 36, 4,false);
INSERT INTO pertence VALUES (4, 75, 4,false);
INSERT INTO pertence VALUES (4, 34, 4,false);
INSERT INTO pertence VALUES (4, 33, 4,false);
INSERT INTO pertence VALUES (5, 5, 1,false);
INSERT INTO pertence VALUES (5, 101, 30,false);
INSERT INTO pertence VALUES (5, 100, 4,false);
INSERT INTO pertence VALUES (5, 99, 4,false);
INSERT INTO pertence VALUES (5, 98, 4,false);
INSERT INTO pertence VALUES (5, 35, 4,false);
INSERT INTO pertence VALUES (5, 36, 4,false);
INSERT INTO pertence VALUES (5, 75, 4,false);
INSERT INTO pertence VALUES (5, 34, 4,false);
INSERT INTO pertence VALUES (5, 33, 4,false);

--INSERT INTO participa VALUES (deck_id, evento_id);
INSERT INTO participa VALUES (1,1);
INSERT INTO participa VALUES (4,1);

-----------------------------------------------------TESTE DE TRIGGERS-----------------------------------------------------

--Relacao Participa
	--Formato deck incompátivel com evento
    --INSERT INTO participa VALUES (2,1);
	--Estourar limite de participantes
	--INSERT INTO participa VALUES (1,3);
	--INSERT INTO participa VALUES (4,3);
	--INSERT INTO participa VALUES (5,3);
	--Deck não tem minimo de cartas
	--INSERT INTO participa VALUES (3,2)

--Tabela Eventos
	--Edicao de formato do evento com participantes incritos
	--UPDATE eventos SET formato_id = 1 WHERE id = 1;

--Relacao Pertence
	--Quantidade de cartas inseridas não respeita limite de formato
	--INSERT INTO pertence VALUES (2, 20, 2,false);
	--Insercao de carta banida num formato
	--INSERT INTO pertence VALUES (1, 2, 3,false);
    --Deck com limite de cartas as ser excedido
	--INSERT INTO pertence VALUES (2, 10, 1,false);
    --Insercao de comandante em deck que ja tem comandante
	--INSERT INTO pertence VALUES (2, 10, 1,true);
	--Insercao de carta com cor imcompativel com comandante
	--INSERT INTO pertence VALUES (2, 11, 1,false);
	--Insercao num deck que precisa de comandante sem comandante
	--INSERT INTO pertence VALUES (6, 11, 1,false);

--Relacao Colore
	--Adicionar cor a carta incolor
	--INSERT INTO colore VALUES (7, 2);
	--Tenta colocar carta com cor como incolot
	--INSERT INTO colore VALUES (8, 1);
	
--Adições no trabalho
	--UPDATE EM DECK
	--UPDATE decks SET formato_id = 1 WHERE id = 1;
	--UPDATE EM FORMATO
	--UPDATE formatos SET qtd_repetidas = 1 WHERE id = 1;
	--UPDATE EM TIPO_CARTA
	--UPDATE tipos_cartas SET basic_land = false WHERE id = 2;
	--UPDATE EM BANIDA
	--INSERT INTO bane VALUES (5, 2);
	--UPDATE bane SET formato_id = 1 WHERE carta_id = 9;
	--UPDATE EM DECK
	--UPDATE decks SET formato_id = 2 WHERE id = 2; 
	--UPDATE decks SET formato_id = 1 WHERE id = 5; 

-----------------------------------------------------CONSULTAS-----------------------------------------------------

--SELECT cartas_mais_jogadas(id_formato, n_ranking) 
--SELECT cartas_mais_jogadas(1, 5) 









