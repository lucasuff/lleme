CREATE TABLE bairro (
  bairro_id integer NOT NULL,
  nome character varying NOT NULL,
  CONSTRAINT bairro_pk PRIMARY KEY
  (bairro_id));

CREATE TABLE municipio (
  municipio_id integer NOT NULL,
  nome character varying NOT NULL,
  CONSTRAINT municipio_pk PRIMARY KEY
  (municipio_id));

CREATE TABLE antena (
  antena_id integer NOT NULL,
  bairro_id integer NOT NULL,
  municipio_id integer NOT NULL,
  CONSTRAINT antena_pk PRIMARY KEY
  (antena_id),
  CONSTRAINT bairro_fk FOREIGN KEY
  (bairro_id) REFERENCES bairro
  (bairro_id),
  CONSTRAINT municipio_fk FOREIGN KEY
  (municipio_id) REFERENCES municipio
  (municipio_id));

CREATE TABLE ligacao (
  ligacao_id bigint NOT NULL,
  numero_orig integer NOT NULL,
  numero_dest integer NOT NULL,
  antena_orig integer NOT NULL,
  antena_dest integer NOT NULL,
  inicio timestamp NOT NULL,
  fim timestamp NOT NULL,
  CONSTRAINT ligacao_pk PRIMARY KEY
  (ligacao_id),
  CONSTRAINT antena_orig_fk FOREIGN KEY
  (antena_orig) REFERENCES antena
  (antena_id),
  CONSTRAINT antena_dest_fk FOREIGN KEY
  (numero_dest) REFERENCES antena
  (antena_id));


CREATE OR REPLACE FUNCTION public.report(p_d1 timestamp,p_d2 timestamp)
  RETURNS TABLE(
	bairro_orig character varying,
	municipio_orig character varying,
	bairro_dest character varying,
	municipio_dest character varying,
	duracao double precision
  ) AS $BODY$
DECLARE
BEGIN
	RETURN
	QUERY
	WITH
	  t1 AS (SELECT
		      t2.bairro_id AS bairro_orig,
		      t2.municipio_id AS municipio_orig,
		      t3.bairro_id AS bairro_dest,
		      t3.municipio_id AS municipio_dest,
		      (EXTRACT (EPOCH FROM LEAST(p_d2,t1.fim))
		      - EXTRACT (EPOCH FROM GREATEST(p_d2,t1.inicio)))/60
               AS duracao
		 FROM ligacao t1
		      INNER JOIN antena t2 ON t2.antena_id = t1.antena_orig
		      INNER JOIN antena t3 ON t3.antena_id = t1.antena_dest
		 WHERE
		      t1.inicio BETWEEN p_d1 AND p_d2
		      OR t1.fim BETWEEN p_d1 AND p_d2
		 FOR SHARE OF t1),
	  t2 AS (SELECT
		      t1.bairro_orig,
		      t1.municipio_orig,
		      t1.bairro_dest,
		      t1.municipio_dest,
		      AVG(t1.duracao) AS duracao_media
		 FROM t1
		 GROUP BY
		      t1.bairro_orig,
		      t1.municipio_orig,
		      t1.bairro_dest,
		      t1.municipio_dest)
	SELECT
	     t3.nome AS bairro_orig,
        t4.nome AS municipio_orig,
	     t5.nome AS bairro_dest,
	     t6.nome AS municipio_dest,
	     t2.duracao_media
	FROM t2
	     INNER JOIN bairro t3 ON t3.bairro_id = t2.bairro_orig
	     INNER JOIN municipio t4 ON t4.municipio_id = t2.municipio_orig
	     INNER JOIN bairro t5 ON t5.bairro_id = t2.bairro_dest
	     INNER JOIN municipio t6 ON t6.municipio_id = t2.municipio_dest
	ORDER BY t2.duracao_media DESC;
	RETURN;
END;
$BODY$ LANGUAGE plpgsql;
