DROP TABLE if exists bairro cascade;
CREATE TABLE bairro (
  bairro_id integer NOT NULL,
  nome character varying NOT NULL,
  CONSTRAINT bairro_pk PRIMARY KEY
  (bairro_id));

DROP TABLE if exists municipio cascade;
CREATE TABLE municipio (
  municipio_id integer NOT NULL,
  nome character varying NOT NULL,
  CONSTRAINT municipio_pk PRIMARY KEY
  (municipio_id));

DROP TABLE if exists antena cascade;
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

DROP TABLE if exists ligacao cascade;
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

create or replace function media(d1 date, d2 date)
returns table (municipio_orig varchar, bairro_orig varchar, municipio_dest varchar, bairro_dest varchar, duracao float) as $$
declare
    c1 cursor for select distinct m_o, b_o, m_d, b_d
                    from ligacao l inner join antena a1 on a1.antena_id = l.antena_orig
                                    inner join antena a2 on a2.antena_id = l.antena_dest
                                    inner ;
begin

end; $$ language plpgsql;
