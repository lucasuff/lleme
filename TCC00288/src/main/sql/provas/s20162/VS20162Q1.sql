drop table if exists pais cascade;
CREATE TABLE pais(
  codigo TEXT NOT NULL primary key,
  nome TEXT NOT NULL,
  capital TEXT NOT NULL,
  populacao TEXT NOT NULL);

drop table if exists provincia cascade;
CREATE TABLE provincia(
  nome TEXT NOT NULL primary key,
  pais TEXT NOT NULL,
  area REAL NOT NULL,
  populacao INTEGER NOT NULL,
  capital TEXT NOT NULL,
  CONSTRAINT provincia_pais_fk FOREIGN
  KEY (pais) REFERENCES pais(codigo)
);

INSERT INTO pais values ('BR','Brasil','Brasilia','200M');
INSERT INTO provincia values ('Rio','BR',10.0,8,'Rio');
INSERT INTO provincia values ('sao paulo','BR',9.0,8,'sao paulo');
INSERT INTO provincia values ('Minas','BR',8.0,7,'BH');
INSERT INTO provincia values ('Espirito Santo','BR',7.0,7,'Vitoria');

drop function if exists computeMedianArea(VARCHAR) cascade;
CREATE OR REPLACE FUNCTION computeMedianArea(p_pais VARCHAR) RETURNS NUMERIC AS $$
    DECLARE
        r1 RECORD;
        r2 RECORD;
        provincias INT;
        i INT;
        mediana NUMERIC;
        curs CURSOR FOR SELECT provincia.area
                        FROM pais JOIN provincia ON pais.codigo = provincia.pais
                        WHERE pais.nome = p_pais
                        ORDER BY provincia.area;
    BEGIN
        IF p_pais IS NOT NULL THEN
            SELECT COUNT(DISTINCT p.nome)
            INTO provincias
            FROM pais c JOIN provincia p ON c.codigo = p.pais
            WHERE c.nome = p_pais;
            OPEN curs;

            i := 0;
            mediana := 0;
            LOOP FETCH curs INTO r1;
                EXIT WHEN NOT FOUND;
                i := i + 1;
                IF i = ROUND(provincias::numeric/2) THEN
                    IF provincias%2 = 0 THEN
                        FETCH curs into r2;
                        mediana = (r1.area + r2.area)/2;
                        EXIT;
                    ELSE
                        mediana = r1.area;
                    END IF;
                END IF;
            END LOOP;
            CLOSE curs;
        END IF;
        RETURN mediana;
    END; $$ LANGUAGE PLPGSQL;

select computeMedianArea('Brasil');
