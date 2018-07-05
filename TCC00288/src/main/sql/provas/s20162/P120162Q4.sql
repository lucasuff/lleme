CREATE TABLE empregado (
  empregado_id integer NOT NULL,
  nome character varying NOT NULL,
  salario real NOT NULL,
  adicional_dep real NOT NULL,
  CONSTRAINT empregado_pk PRIMARY KEY
  (empregado_id)
);

CREATE TABLE dependente (
  empregado_id integer NOT NULL,
  seq smallint NOT NULL,
  nome character varying NOT NULL,
  CONSTRAINT dependente_pk PRIMARY KEY
  (empregado_id, seq),
  CONSTRAINT empregado_fk FOREIGN KEY
  (empregado_id) REFERENCES empregado
  (empregado_id));


CREATE OR REPLACE FUNCTION public.update_empregado()
  RETURNS void AS
$BODY$
DECLARE
    c_empregado CURSOR FOR SELECT * FROM empregado FOR UPDATE OF
    empregado;
    v_count integer;
BEGIN
	FOR r IN c_empregado LOOP
        select count(seq) into v_count from dependente where
        empregado_id = r.empregado_id;
        if v_count > 3 then
            UPDATE empregado SET r.adicional_dep = r.adicional_dep *
            (1 + v_count * 0.05) WHERE CURRENT OF c_empregado;
        end if;
	END LOOP;
END;
$BODY$ LANGUAGE plpgsql
