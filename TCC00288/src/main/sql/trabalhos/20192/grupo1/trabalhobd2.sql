CREATE SCHEMA public;

CREATE TABLE public.usuario
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    nome character(100) NOT NULL,
    email character(100) NOT NULL,
    celular character(15) NOT NULL,
    senha character(128) NOT NULL,
    data_nascimento date NOT NULL,
    logado smallint,
    ultimo_login date,
    PRIMARY KEY (id)
);


CREATE TABLE public.motorista
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_usuario integer NOT NULL,
    cpf character(14),
    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.passageiro
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ) ,
    id_usuario integer NOT NULL,
    cpf character(14) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.gestor
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_usuario integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.tarifa
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    bandeirada money NOT NULL,
    valor_minimo money NOT NULL,
    minuto money NOT NULL,
    km money NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.categoria
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ), 
    id_tarifa integer NOT NULL,
    nome character(40) NOT NULL,
    PRIMARY KEY (id),
       FOREIGN KEY (id_tarifa)
        REFERENCES public.tarifa (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION

);




CREATE TABLE public.veiculo
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_motorista integer NOT NULL,
    modelo character(100) NOT NULL,
    cor character(30) NOT NULL,
    placa character(7) NOT NULL,
    marca character(30) NOT NULL,
    categoria_id integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_motorista)
        REFERENCES public.motorista (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (categoria_id)
        REFERENCES public.categoria (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);



CREATE TABLE public.cartao
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_passageiro integer NOT NULL,
    cpf_cartao character(14) NOT NULL,
    numero character(128) NOT NULL,
    cvv character(3) NOT NULL,
    data_expiracao date NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_passageiro)
        REFERENCES public.passageiro (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.cupom
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    "código" character(5) COLLATE pg_catalog."default" NOT NULL,
    desconto money NOT NULL,
    quantidade integer NOT NULL,
    primeira_corrida smallint NOT NULL,
    CONSTRAINT cupom_pkey PRIMARY KEY (id)
);


CREATE TABLE public.notificacao
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_gestor integer NOT NULL,
    id_passageiro integer,
    id_motorista integer,
    "condição_envio" integer,
    mensagem text NOT NULL,
    data_hora time without time zone NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_gestor)
        REFERENCES public.gestor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (id_passageiro)
        REFERENCES public.passageiro (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (id_motorista)
        REFERENCES public.motorista (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


COMMENT ON COLUMN public.notificacao."condição_envio"
    IS '0 -> Envia para todos
1 -> Envia apenas para o id_passageiro ou id_motorista especificado';


CREATE TABLE public.avaliacao_passageiro
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_motorista integer NOT NULL,
    id_passageiro integer NOT NULL,
    nota integer NOT NULL,
    mensagem text,
    PRIMARY KEY (id),
    FOREIGN KEY (id_motorista)
        REFERENCES public.motorista (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (id_passageiro)
        REFERENCES public.passageiro (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.avaliacao_motorista
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_motorista integer NOT NULL,
    id_passageiro integer NOT NULL,
    nota integer NOT NULL,
    mensagem text,
    PRIMARY KEY (id),
    FOREIGN KEY (id_motorista)
        REFERENCES public.motorista (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (id_passageiro)
        REFERENCES public.passageiro (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE TABLE public."endereço_favorito"
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_passageiro integer NOT NULL,
    endereco character(255) NOT NULL,
    bairro character(50) NOT NULL,
    lat character(50),
    lng character(50),
    PRIMARY KEY (id),
    FOREIGN KEY (id_passageiro)
        REFERENCES public.passageiro (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.pais
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    nome character(40) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE public.estado
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_pais integer NOT NULL,
    nome character(50) NOT NULL,
    sigla character(2) NOT NULL,
    timezone character(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_pais)
        REFERENCES public.pais (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.cidade
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_estado integer,
    nome character(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_estado)
        REFERENCES public.estado (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);



CREATE TABLE public.corrida
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_motorista integer NOT NULL,
    id_passageiro integer NOT NULL,
    id_tarifa integer NOT NULL,
    id_cidade integer NOT NULL,
    estimativa_km double precision NOT NULL,
    estimativa_minutos integer NOT NULL,
    estimativa_valor money NOT NULL,
    km double precision NOT NULL,
    minutos integer NOT NULL,
    valor money NOT NULL,
    data_hora time without time zone NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_motorista)
        REFERENCES public.motorista (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (id_passageiro)
        REFERENCES public.passageiro (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (id_tarifa)
        REFERENCES public.tarifa (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE public.corrida
    ADD FOREIGN KEY (id_cidade)
    REFERENCES public.cidade (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;




ALTER TABLE public.corrida
    ADD COLUMN id_cupom integer;

ALTER TABLE public.corrida
    ADD COLUMN valor_desconto money;
ALTER TABLE public.corrida
    ADD FOREIGN KEY (id_cupom)
    REFERENCES public.cupom (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;





CREATE TABLE public.transacao
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_corrida integer NOT NULL,
    valor money NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_corrida)
        REFERENCES public.corrida (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.transacao_dinheiro
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_transacao integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_transacao)
        REFERENCES public.transacao (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.transacao_cartao
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_transacao integer NOT NULL,
    id_cartao integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_transacao)
        REFERENCES public.transacao (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (id_cartao)
        REFERENCES public.cartao (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.conta
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_transacao integer NOT NULL,
    id_motorista integer NOT NULL,
    valor money,
    data_limite date,
    PRIMARY KEY (id),
    FOREIGN KEY (id_transacao)
        REFERENCES public.transacao (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    FOREIGN KEY (id_motorista)
        REFERENCES public.motorista (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.posicao
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    id_motorista integer NOT NULL,
    lat float NOT NULL,
    lng float NOT NULL,
    data_hora time without time zone NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_motorista)
        REFERENCES public.motorista (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);






CREATE OR REPLACE FUNCTION calculate_distance(lat1 float, lon1 float, lat2 float, lon2 float, units varchar)
RETURNS float AS $$
    DECLARE
        dist float = 0;
        radlat1 float;
        radlat2 float;
        theta float;
        radtheta float;
    BEGIN
        IF lat1 = lat2 OR lon1 = lon2
            THEN RETURN dist;
        ELSE
            radlat1 = pi() * lat1 / 180;
            radlat2 = pi() * lat2 / 180;
            theta = lon1 - lon2;
            radtheta = pi() * theta / 180;
            dist = sin(radlat1) * sin(radlat2) + cos(radlat1) * cos(radlat2) * cos(radtheta);

            IF dist > 1 THEN dist = 1; END IF;

            dist = acos(dist);
            dist = dist * 180 / pi();
            dist = dist * 60 * 1.1515;

            IF units = 'K' THEN dist = dist * 1.609344; END IF;
            IF units = 'N' THEN dist = dist * 0.8684; END IF;

            RETURN dist;
        END IF;
    END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION distribuir_corrida(lt float, ln float)
RETURNS INTEGER AS $$
    DECLARE
      c CURSOR FOR SELECT * FROM motorista m INNER JOIN usuario u ON u.id = m.id_usuario WHERE u.logado = 1;
       r RECORD; 
       dist FLOAT;
   	   d FLOAT = 0;
       id_f INTEGER = 0;
       current_motorista RECORD;
    BEGIN
  		FOR r IN c LOOP
  			SELECT id_motorista,lat,lng INTO current_motorista FROM posicao WHERE id_motorista = r.id ORDER BY data_hora DESC LIMIT 1;
  			SELECT calculate_distance(lt,ln,current_motorista.lat,current_motorista.lng,'M') INTO dist;
  			IF ( d = 0 ) THEN
  				d = dist;
  				id_f = current_motorista.id_motorista;
  			END IF;
  			IF ( d > dist ) THEN
  				d = dist;
  				id_f = current_motorista.id_motorista;
  			END IF;
  		END LOOP;
  		RETURN id_f;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION calcular_nota(id_us integer)
RETURNS FLOAT AS $$
    DECLARE
       n FLOAT;
       media FLOAT;
       qtd INTEGER;
       current_motorista RECORD;
       current_passagerio RECORD;
    BEGIN
		SELECT * INTO current_motorista FROM motorista WHERE id_usuario = id_us;
		IF( current_motorista IS NOT NULL) THEN
			SELECT count(*) INTO qtd FROM avaliacao_motorista WHERE id_motorista = current_motorista.id;
			SELECT nota INTO n FROM avaliacao_motorista WHERE id_motorista = current_motorista.id;
			media = n/qtd;
			RETURN media;
		END IF;

		SELECT * INTO current_passagerio FROM passageiro WHERE id = id_us;
		IF( current_motorista IS NOT NULL) THEN
			SELECT count(*) INTO qtd FROM avaliacao_passageiro WHERE id_passageiro = current_passageiro.id;
			SELECT nota INTO n FROM avaliacao_passageiro WHERE id_passageiro = current_passageiro.id;
			media = n/qtd;
			RETURN media;
		END IF;
        
        RETURN NULL;

    END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION del_tarifa()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM tarifa WHERE old.id_tarifa = tarifa.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_cat1 BEFORE DELETE ON categoria
FOR EACH ROW EXECUTE PROCEDURE del_tarifa();

CREATE OR REPLACE FUNCTION set_default_categoria_veiculo()
RETURNS trigger AS $$
    BEGIN
      UPDATE veiculo SET veiculo.categoria_id = 1 WHERE veiculo.categoria_id = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_cat2 BEFORE DELETE ON categoria
FOR EACH ROW EXECUTE PROCEDURE set_default_categoria_veiculo();

CREATE OR REPLACE FUNCTION set_default_categoria_corrida()
RETURNS trigger AS $$
    DECLARE
    i RECORD;
    BEGIN
     SELECT id_tarifa INTO i FROM categoria WHERE categoria.id = 8;
      UPDATE corrida SET id_tarifa = i.id_tarifa WHERE corrida.id_tarifa = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_cat3 BEFORE DELETE ON tarifa
FOR EACH ROW EXECUTE PROCEDURE set_default_categoria_corrida();


CREATE OR REPLACE FUNCTION del_aval_motorista_motorista()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM avaliacao_motorista WHERE avaliacao_motorista.id_motorista = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_aval_motorista1 BEFORE DELETE ON motorista
FOR EACH ROW EXECUTE PROCEDURE del_aval_motorista_motorista();

CREATE OR REPLACE FUNCTION del_aval_motorista_passageiro()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM avaliacao_motorista WHERE avaliacao_motorista.id_passageiro = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_aval_motorista2 BEFORE DELETE ON passageiro
FOR EACH ROW EXECUTE PROCEDURE del_aval_motorista_passageiro();

CREATE OR REPLACE FUNCTION del_aval_passageiro_motorista()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM avaliacao_passageiro WHERE avaliacao_passageiro.id_motorista = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION del_end_fav()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM endereço_favorito WHERE endereço_favorito.id_passageiro = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_end BEFORE DELETE ON passageiro
FOR EACH ROW EXECUTE PROCEDURE del_end_fav();

CREATE OR REPLACE FUNCTION del_not_pass()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM notificacao WHERE notificacao.id_passageiro = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_not_pass BEFORE DELETE ON passageiro
FOR EACH ROW EXECUTE PROCEDURE del_not_pass();

CREATE OR REPLACE FUNCTION del_cartao_passageiro()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM cartao WHERE cartao.id_passageiro = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_card1 BEFORE DELETE ON passageiro
FOR EACH ROW EXECUTE PROCEDURE del_cartao_passageiro();

CREATE OR REPLACE FUNCTION del_trans_card()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM transacao_cartao WHERE transacao_cartao.id_cartao = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_trans_card1 BEFORE DELETE ON cartao
FOR EACH ROW EXECUTE PROCEDURE del_trans_card();



CREATE TRIGGER del_aval_passageiro1 BEFORE DELETE ON motorista
FOR EACH ROW EXECUTE PROCEDURE del_aval_passageiro_motorista();

CREATE OR REPLACE FUNCTION del_aval_passageiro_passageiro()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM avaliacao_passageiro WHERE avaliacao_passageiro.id_passageiro = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_corrida_motorista BEFORE DELETE ON passageiro
FOR EACH ROW EXECUTE PROCEDURE del_aval_passageiro_passageiro();

CREATE OR REPLACE FUNCTION del_corridas_motorista()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM corrida WHERE corrida.id_motorista = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_corridas1 BEFORE DELETE ON motorista
FOR EACH ROW EXECUTE PROCEDURE del_corridas_motorista();

CREATE OR REPLACE FUNCTION del_corridas_passageiro()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM corrida WHERE corrida.id_passageiro = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_corridas2 BEFORE DELETE ON passageiro
FOR EACH ROW EXECUTE PROCEDURE del_corridas_passageiro();

CREATE OR REPLACE FUNCTION del_veiculo()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM veiculo WHERE veiculo.id_motorista = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_veiculo_motorista BEFORE DELETE ON motorista
FOR EACH ROW EXECUTE PROCEDURE del_veiculo();

CREATE OR REPLACE FUNCTION del_corrida_motorista()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM transacao WHERE transacao.id_corrida = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_corrida_motorista BEFORE DELETE ON corrida
FOR EACH ROW EXECUTE PROCEDURE del_corrida_motorista();

CREATE OR REPLACE FUNCTION del_transacao_cartao()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM transacao_cartao WHERE transacao_cartao.id_transacao = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_transacao1 BEFORE DELETE ON transacao
FOR EACH ROW EXECUTE PROCEDURE del_transacao_cartao();


CREATE OR REPLACE FUNCTION del_pos()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM posicao WHERE posicao.id_motorista = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_pos1 BEFORE DELETE ON motorista
FOR EACH ROW EXECUTE PROCEDURE del_pos();


CREATE OR REPLACE FUNCTION del_notificacao()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM notificacao WHERE notificacao.id_motorista = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_not1 BEFORE DELETE ON motorista
FOR EACH ROW EXECUTE PROCEDURE del_notificacao();

CREATE OR REPLACE FUNCTION del_conta()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM conta WHERE conta.id_transacao = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_conta1 BEFORE DELETE ON transacao
FOR EACH ROW EXECUTE PROCEDURE del_conta();

CREATE OR REPLACE FUNCTION del_transacao_dinheiro()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM transacao_dinheiro WHERE transacao_dinheiro.id_transacao = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_transacao2 BEFORE DELETE ON transacao
FOR EACH ROW EXECUTE PROCEDURE del_transacao_dinheiro();


CREATE OR REPLACE FUNCTION delete_estado()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM estado WHERE estado.id_pais = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_estado BEFORE DELETE ON pais
FOR EACH ROW EXECUTE PROCEDURE delete_estado();

CREATE OR REPLACE FUNCTION delete_cidade()
RETURNS trigger AS $$
    BEGIN
      DELETE FROM cidade WHERE cidade.id_estado = old.id;
      RETURN OLD;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER del_cidade BEFORE DELETE ON estado
FOR EACH ROW EXECUTE PROCEDURE delete_cidade();


