DROP TABLE IF EXISTS venda;

DROP TABLE IF EXISTS projecoes;

CREATE TABLE venda (
    ano_mes integer,
    unidade integer,
    vendedor varchar(80),
    produto varchar(80),
    valor float
);

CREATE TABLE projecoes (
    produto varchar(80),
    anomes integer,
    projecao float
);

INSERT INTO venda
    VALUES (201706, 1, 'carlinhos', 'gol quadrado 1990', 10000.0);

INSERT INTO venda
    VALUES (201706, 1, 'marcos', 'gol quadrado 1990', 20000.0);

INSERT INTO venda
    VALUES (201706, 1, 'Chuck Norris', 'gol quadrado 1990', 3000.0);

INSERT INTO venda
    VALUES (201705, 1, 'carlinhos', 'gol quadrado 1990', 2000.0);

INSERT INTO venda
    VALUES (201705, 1, 'marcos', 'gol quadrado 1990', 3456.0);

INSERT INTO venda
    VALUES (201705, 1, 'Chuck Norris', 'gol quadrado 1990', 5500.0);

INSERT INTO venda
    VALUES (201704, 1, 'carlinhos', 'gol quadrado 1990', 9.0);

INSERT INTO venda
    VALUES (201704, 1, 'marcos', 'gol quadrado 1990', 299.99);

INSERT INTO venda
    VALUES (201704, 1, 'Chuck Norris', 'gol bola 1996', 69.90);

INSERT INTO venda
    VALUES (201706, 1, 'carlinhos', 'gol quadrado 1990', 10000.0);

INSERT INTO venda
    VALUES (201706, 1, 'marcos', 'gol quadrado 1990', 20000.0);

INSERT INTO venda
    VALUES (201706, 1, 'Chuck Norris', 'gol quadrado 1990', 3000.0);

INSERT INTO venda
    VALUES (201705, 1, 'carlinhos', 'gol quadrado 1990', 2000.0);

INSERT INTO venda
    VALUES (201705, 1, 'marcos', 'gol quadrado 1990', 3456.0);

INSERT INTO venda
    VALUES (201705, 1, 'Chuck Norris', 'gol quadrado 1990', 5500.0);

INSERT INTO venda
    VALUES (201704, 1, 'carlinhos', 'gol quadrado 1990', 9.0);

INSERT INTO venda
    VALUES (201704, 1, 'marcos', 'gol quadrado 1990', 299.99);

INSERT INTO venda
    VALUES (201704, 1, 'Chuck Norris', 'gol bola 1996', 69.90);

INSERT INTO venda
    VALUES (201703, 1, 'carlinhos', 'gol bola 1996', 200000.0);

INSERT INTO venda
    VALUES (201702, 1, 'marcos', 'gol bola 1996', 1500.0);

INSERT INTO venda
    VALUES (201702, 1, 'Chuck Norris', 'gol bola 1996', 5000.0);

INSERT INTO venda
    VALUES (201701, 1, 'carlinhos', 'gol bola 1996', 70000.0);

INSERT INTO venda
    VALUES (201701, 1, 'marcos', 'gol bola 1996', 3000.0);

INSERT INTO venda
    VALUES (201701, 1, 'Chuck Norris', 'gol bola 1996', 7000.0);

INSERT INTO venda
    VALUES (201701, 1, 'carlinhos', 'gol bola 1996', 20.0);

INSERT INTO venda
    VALUES (201701, 1, 'marcos', 'gol bola 1996', 2.0);

INSERT INTO venda
    VALUES (201701, 1, 'Chuck Norris', 'gol bola 1996', 500.0);

DROP FUNCTION IF EXISTS transposta (float[][]);

CREATE OR REPLACE FUNCTION transposta (matriz float[][])
    RETURNS float[][]
    AS $$
DECLARE
    resultado float[][];
    coluna float[];
BEGIN
    resultado = '{}';
    FOR i IN 1..array_length(matriz,
        2)
    LOOP
        coluna = '{}';
        FOR j IN 1..array_length(matriz,
            1)
        LOOP
            coluna = array_append(coluna, matriz[j][i]);
        END LOOP;
        resultado = array_cat(resultado, ARRAY[coluna]);
    END LOOP;
    RETURN resultado;
END;
$$
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS multiplica (float[][], float[][]);

CREATE OR REPLACE FUNCTION multiplica (a float[][], b float[][])
    RETURNS float[][]
    AS $$
DECLARE
    acc float;
    linha float[];
    resultado float[][];
BEGIN
    FOR i IN 1..array_length(a,
        1)
    LOOP
        FOR j IN 1..array_length(b,
            2)
        LOOP
            acc = 0;
            FOR k IN 1..array_length(a,
                2)
            LOOP
                acc = acc + a[i][k] * b[k][j];
            END LOOP;
            linha = array_append(linha, acc);
        END LOOP;
        resultado = array_cat(resultado, ARRAY[linha]);
    END LOOP;
    RETURN resultado;
END;
$$
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS get_t1 (varchar);

CREATE OR REPLACE FUNCTION get_t1 (prod varchar)
    RETURNS TABLE (
        ano_mes integer, valor float
)
    AS $$
DECLARE
BEGIN
    RETURN query
    SELECT
        venda.ano_mes,
        sum(venda.valor) AS valor
    FROM
        venda
    WHERE
        venda.produto = prod
    GROUP BY
        venda.ano_mes;
END;
$$
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS get_x (varchar);

CREATE OR REPLACE FUNCTION get_x (prod varchar)
    RETURNS float[][]
    AS $$
DECLARE
    x float[][];
    aux float[];
    curs CURSOR (prod varchar)
    FOR
        SELECT
            t1.ano_mes
        FROM
            get_t1 (prod) AS t1;
BEGIN
    x = '{}';
    FOR linha IN curs (prod)
    LOOP
        aux = ARRAY[linha.ano_mes::float, 1.];
        x = array_cat(x, ARRAY[aux]);
    END LOOP;
    RETURN x;
END;
$$
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS get_r (varchar);

CREATE OR REPLACE FUNCTION get_r (prod varchar)
    RETURNS float[][]
    AS $$
DECLARE
    resultado float[][];
    linhas_de_t1 CURSOR (prod varchar)
    FOR
        SELECT
            valor
        FROM
            get_t1 (prod);
BEGIN
    FOR linha IN linhas_de_t1 (prod)
    LOOP
        resultado = array_cat(resultado, ARRAY[linha.valor]);
    END LOOP;
    RETURN resultado;
END;
$$
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS projecao_do_produto (integer, varchar);

CREATE OR REPLACE FUNCTION projecao_do_produto (anomes integer, produto varchar)
    RETURNS float
    AS $$
DECLARE
    x float[][];
    x_t float[][];
    r float[][];
    v float[];
BEGIN
    x = get_x (produto);
    x_t = transposta (x);
    r = get_r (produto);
    v = resolver (multiplica (x_t, x), multiplica (x_t, r));
    RETURN v[1] * anomes + v[2];
END;
$$
LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS projecoes (varchar);

CREATE OR REPLACE FUNCTION projecoes (mes integer)
    RETURNS TABLE (
        produto varchar(80), anomes integer, projecao float
)
    AS $$
DECLARE
    projecao float;
    produtos CURSOR FOR SELECT DISTINCT
            venda.produto
        FROM
            venda;
BEGIN
    FOR produto IN produtos LOOP
        projecao = projecao_do_produto (mes, produto.produto);
        INSERT INTO projecoes
        VALUES (produto, mes, projecao);
    END LOOP;
    RETURN query
    SELECT
        *
    FROM
        projecoes;
END;
$$
LANGUAGE plpgsql;

SELECT
    projecoes (201707);

