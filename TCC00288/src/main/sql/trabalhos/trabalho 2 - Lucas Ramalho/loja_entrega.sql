DROP DATABASE loja;

CREATE DATABASE loja
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'C'
       LC_CTYPE = 'C'
       CONNECTION LIMIT = -1;



CREATE SEQUENCE public.pedido_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.entrega_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.pagto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.tracking_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE public.status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.pedido (
    cod_pedido integer DEFAULT nextval('public.pedido_id_seq') NOT NULL,
    cod_cliente integer NOT NULL,
    cod_status integer NOT NULL,
    cod_tipo_pagto integer NOT NULL,
    valor_total numeric(8,2) NOT NULL,
    cod_endereco_entrega integer NOT NULL,
    data_status TIMESTAMP
);

CREATE TABLE public.entrega (
    cod_entrega integer DEFAULT nextval('public.entrega_id_seq') NOT NULL,
    cod_pedido integer NOT NULL,
    previsto_para DATE,
    cod_status integer NOT NULL
);

CREATE TABLE public.tipo_pagto (
    cod_tipo_pagto integer DEFAULT nextval('public.pagto_id_seq') NOT NULL,
    nome_tipo_pagto VARCHAR(20)
);

CREATE TABLE public.tracking (
    cod_tracking integer DEFAULT nextval('public.tracking_id_seq') NOT NULL,
    cod_entrega integer NOT NULL,
    cod_status integer NOT NULL,
    cod_endereco_entrega integer NOT NULL
);

CREATE TABLE public.status (
    cod_status integer DEFAULT nextval('public.status_id_seq') NOT NULL,
    descricao VARCHAR(40)
);

INSERT INTO tipo_pagto VALUES (nextval('pagto_id_seq'), 'a vista');
INSERT INTO tipo_pagto VALUES (nextval('pagto_id_seq'), 'a prazo');

INSERT INTO status VALUES (nextval('status_id_seq'), 'aguardando confirmacao do pagamento');
INSERT INTO status VALUES (nextval('status_id_seq'), 'postando produto');
INSERT INTO status VALUES (nextval('status_id_seq'), 'produto em trajeto');
INSERT INTO status VALUES (nextval('status_id_seq'), 'entrega concluida');

INSERT INTO pedido VALUES (nextval('pedido_id_seq'), 123, 2, 2, 4800, 24344150, '2019-11-20 10:23:52');
INSERT INTO pedido VALUES (nextval('pedido_id_seq'), 123, 4, 1, 120, 24344150, '2019-10-13 16:40:37');
INSERT INTO pedido VALUES (nextval('pedido_id_seq'), 237, 1, 2, 3000, 23244120, '2019-11-26 8:15:12');
INSERT INTO pedido VALUES (nextval('pedido_id_seq'), 245, 3, 1, 350, 26523447, '2019-11-23 14:37:12');
INSERT INTO pedido VALUES (nextval('pedido_id_seq'), 123, 3, 1, 4800, 24344150, '2019-11-20 22:13:11');
INSERT INTO pedido VALUES (nextval('pedido_id_seq'), 237, 4, 2, 120, 23244120, '2019-10-18 19:30:54');

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (cod_pedido),
    ADD CONSTRAINT cod_status_fkey FOREIGN KEY (cod_status) REFERENCES public.tracking(cod_status) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY public.entrega
    ADD CONSTRAINT entrega_pkey PRIMARY KEY (cod_entrega),
    ADD CONSTRAINT cod_status_fkey FOREIGN KEY (cod_status) REFERENCES public.pedido(cod_status) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY public.tipo_pagto
    ADD CONSTRAINT entrega_pkey PRIMARY KEY (cod_tipo_pagto);

ALTER TABLE ONLY public.tracking
    ADD CONSTRAINT entrega_pkey PRIMARY KEY (cod_tracking);

ALTER TABLE ONLY public.status
    ADD CONSTRAINT entrega_pkey PRIMARY KEY (cod_status);

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT cod_tipo_pagto_id_fkey FOREIGN KEY (cod_tipo_pagto) REFERENCES public.tipo_pagto(cod_tipo_pagto) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY public.entrega
    ADD CONSTRAINT cod_pedido_id_fkey FOREIGN KEY (cod_pedido) REFERENCES public.pedido(cod_pedido) ON UPDATE CASCADE ON DELETE RESTRICT;

ALTER TABLE ONLY public.tracking
    ADD CONSTRAINT cod_pedido_id_fkey FOREIGN KEY (cod_entrega) REFERENCES public.entrega(cod_entrega) ON UPDATE CASCADE ON DELETE RESTRICT;



CREATE FUNCTION public.pedidos_nao_entregues() RETURNS SETOF integer
    LANGUAGE sql
    AS $_$
     SELECT cod_pedido
     FROM entrega
     WHERE cod_status  < 4;
$_$;

CREATE FUNCTION public.total_vendas() RETURNS numeric(8,2)
    LANGUAGE plpgsql
    AS $_$

DECLARE
    cur CURSOR IS SELECT valor_total FROM pedido;
    total NUMERIC(8,2) := 0;

BEGIN
    FOR rec IN cur LOOP
        total := total + rec.valor_total;
    END LOOP;
    return total;
END;     

        
$_$;

CREATE OR REPLACE FUNCTION public.pedidos_atrasados() RETURNS TABLE (cod_pedido INTEGER, data_status TIMESTAMP, previsto_para DATE)
LANGUAGE plpgsql
AS $_$
BEGIN
RETURN QUERY SELECT P.cod_pedido, P.data_status, E.previsto_para
    FROM pedido AS P JOIN entrega AS E ON P.cod_pedido = E.cod_pedido
    WHERE to_char((SELECT NOW()), 'YYYMMMDDD') > to_char(E.previsto_para, 'YYYMMMDDD') AND P.cod_status < 4;
END;
$_$;

CREATE OR REPLACE FUNCTION pedido_por_sede_do_correios() RETURNS TABLE(sede varchar(60), qtd INTEGER)
LANGUAGE plpgsql
AS $_$

DECLARE
cur CURSOR IS SELECT CAST((cod_endereco_entrega/10000000) AS INTEGER) AS digito, count(CAST((cod_endereco_entrega/10000000) AS INTEGER)) AS qtd FROM pedido GROUP BY digito;
dig INTEGER;
sede VARCHAR(60);
BEGIN
FOR c IN cur LOOP
dig = c.digito;
CASE WHEN dig = 1 THEN sede = 'Santos';
WHEN dig = 2 THEN sede = 'Rio de Janeiro';
WHEN dig = 3 THEN sede = 'Belo Horizonte';
WHEN dig = 4 THEN sede = 'Salvador';
WHEN dig = 5 THEN sede = 'Recife';
WHEN dig = 6 THEN sede = 'Fortaleza';
WHEN dig = 7 THEN sede = 'Brasília';
WHEN dig = 8 THEN sede = 'Curitiba';
WHEN dig = 9 THEN sede = 'Porto Alegre';
else sede = 'Não identificada';
END CASE;

RETURN QUERY SELECT sede, CAST(c.qtd AS INTEGER);

END LOOP;
RETURN;
END;
$_$;

CREATE OR REPLACE FUNCTION cria_entrega()
RETURNS trigger
LANGUAGE plpgsql
AS $_$
BEGIN
INSERT INTO public.entrega
(cod_entrega, cod_pedido, previsto_para, cod_status)
VALUES
(nextval('entrega_id_seq'), NEW.cod_pedido, NEW.data_status + interval '10' day, new.cod_status);
RETURN NEW;
END;
$_$;

CREATE OR REPLACE FUNCTION cria_tracking()
RETURNS trigger
LANGUAGE plpgsql
AS $_$
DECLARE
cur CURSOR IS SELECT cod_endereco_entrega FROM public.pedido WHERE cod_pedido = NEW.cod_pedido;
rec INTEGER;
BEGIN
OPEN cur;
FETCH cur INTO rec;
INSERT INTO public.tracking
(cod_tracking, cod_entrega, cod_status, cod_endereco_entrega)
VALUES
(nextval('tracking_id_seq'), NEW.cod_entrega, NEW.cod_status, rec);
CLOSE cur;
RETURN NEW;
END;
$_$;

CREATE OR REPLACE FUNCTION atualiza_status_entrega()
RETURNS trigger
LANGUAGE plpgsql
AS $_$
BEGIN
IF OLD.cod_status <> NEW.cod_status THEN
    UPDATE public.entrega SET cod_status = NEW.cod_status WHERE cod_pedido = NEW."cod_pedido";
    return NEW;
END IF;
END;
$_$;

CREATE OR REPLACE FUNCTION atualiza_status_tracking()
RETURNS trigger
LANGUAGE plpgsql
AS $_$
BEGIN
    IF OLD.cod_status <> NEW.cod_status THEN
    UPDATE public.tracking set cod_status = NEW.cod_status WHERE cod_entrega = NEW."cod_entrega";
    RETURN NEW;
END IF;
END;
$_$;

CREATE TRIGGER cria_entrega
AFTER INSERT ON public.pedido
FOR EACH ROW
EXECUTE PROCEDURE cria_entrega();

CREATE TRIGGER cria_tracking
AFTER INSERT ON public.entrega
FOR EACH ROW
EXECUTE PROCEDURE cria_tracking();

CREATE TRIGGER atualiza_status_entrega
AFTER UPDATE ON public.pedido
FOR EACH ROW
EXECUTE PROCEDURE atualiza_status_entrega();

CREATE TRIGGER atualiza_status_tracking
AFTER UPDATE ON public.entrega
FOR EACH ROW
EXECUTE PROCEDURE atualiza_status_tracking();



