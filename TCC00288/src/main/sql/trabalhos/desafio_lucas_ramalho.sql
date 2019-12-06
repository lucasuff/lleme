DROP TABLE IF EXISTS public.venda;
CREATE TABLE public.venda (
    anoMes INTEGER,
    unidade INTEGER,
    vendedor VARCHAR(60),
    produto VARCHAR(60),
    valor FLOAT
);

INSERT INTO venda
    VALUES (201706, 3, 'americanas', 'notebook', 10000.0);

INSERT INTO venda
    VALUES (201706, 1, 'shoptime', 'notebook', 20000.0);

INSERT INTO venda
    VALUES (201706, 5, 'submarino', 'smartwatch', 3000.0);

INSERT INTO venda
    VALUES (201705, 1, 'americanas', 'notebook', 2000.0);

INSERT INTO venda
    VALUES (201705, 1, 'shoptime', 'notebook', 3456.0);

INSERT INTO venda
    VALUES (201705, 1, 'submarino', 'notebook', 5500.0);

INSERT INTO venda
    VALUES (201704, 1, 'americanas', 'notebook', 9.0);

INSERT INTO venda
    VALUES (201704, 2, 'shoptime', 'smartwatch', 299.99);

INSERT INTO venda
    VALUES (201704, 1, 'submarino', 'iphone', 69.90);

INSERT INTO venda
    VALUES (201706, 8, 'americanas', 'notebook', 10000.0);

INSERT INTO venda
    VALUES (201706, 1, 'shoptime', 'notebook', 20000.0);

INSERT INTO venda
    VALUES (201706, 13, 'submarino', 'notebook', 3000.0);

INSERT INTO venda
    VALUES (201705, 1, 'americanas', 'notebook', 2000.0);

INSERT INTO venda
    VALUES (201705, 1, 'shoptime', 'smartwatch', 3456.0);

INSERT INTO venda
    VALUES (201705, 1, 'submarino', 'notebook', 5500.0);

INSERT INTO venda
    VALUES (201704, 1, 'americanas', 'notebook', 9.0);

INSERT INTO venda
    VALUES (201704, 10, 'shoptime', 'notebook', 299.99);

INSERT INTO venda
    VALUES (201704, 2, 'submarino', 'iphone', 69.90);

INSERT INTO venda
    VALUES (201703, 1, 'americanas', 'iphone', 200000.0);

INSERT INTO venda
    VALUES (201701, 7, 'americanas', 'iphone', 70000.0);

INSERT INTO venda
    VALUES (201701, 1, 'shoptime', 'iphone', 3000.0);

INSERT INTO venda
    VALUES (201701, 1, 'submarino', 'iphone', 7000.0);

INSERT INTO venda
    VALUES (201701, 1, 'americanas', 'iphone', 20.0);

INSERT INTO venda
    VALUES (201701, 3, 'shoptime', 'smartwatch', 2.0);

INSERT INTO venda
    VALUES (201701, 1, 'submarino', 'iphone', 500.0);


CREATE OR REPLACE FUNCTION public.t1(prod VARCHAR(60)) RETURNS TABLE (anoMes INTEGER, valor FLOAT)
LANGUAGE plpgsql
AS $$
BEGIN
RETURN QUERY
SELECT venda.anoMes, sum(venda.valor) FROM venda
WHERE venda.produto = prod
GROUP BY venda.anoMes;
END;
$$;

CREATE OR REPLACE FUNCTION public.criax(produto VARCHAR(60)) RETURNS FLOAT[][]
LANGUAGE plpgsql
AS $$
DECLARE
x FLOAT[][];
auxX FLOAT[];
cur1 CURSOR IS SELECT anoMes FROM t1(produto);
BEGIN
x = '{}';
FOR c IN cur1 LOOP
auxX = '{}';
auxX = ARRAY[c.anoMes::FLOAT,1.0];
x = array_cat(x, ARRAY[auxX]);
END LOOP;
RETURN x;
END;
$$;

CREATE OR REPLACE FUNCTION public.criaxl(x FLOAT[][]) RETURNS FLOAT[][]
LANGUAGE plpgsql
AS $$
DECLARE
col FLOAT[];
xl FLOAT[][];
BEGIN
xl = '{}';
FOR c IN 1..array_upper(x,2) LOOP
col = '{}';
FOR l IN 1..array_upper(x,1) LOOP
col = array_append(col,x[l][c]);
END LOOP;
xl = array_cat(xl,ARRAY[col]);
END LOOP;
RETURN xl;
END;
$$;

CREATE OR REPLACE FUNCTION public.criar(produto VARCHAR(60)) RETURNS FLOAT[]
LANGUAGE plpgsql
AS $$
DECLARE
cur2 CURSOR IS SELECT valor FROM t1(produto);
r FLOAT[][];
aux FLOAT[];
BEGIN
FOR c IN cur2 LOOP
aux = array_append(aux, c.valor);
r = array_cat(r, ARRAY[aux]);
aux = '{}';
END LOOP;
RETURN r;
END;
$$;

CREATE OR REPLACE FUNCTION public.multiplicaMatrizes(a FLOAT[][], b FLOAT[][]) RETURNS FLOAT[][]
LANGUAGE plpgsql
AS $$
DECLARE
resultado FLOAT[][];
val FLOAT;
linha FLOAT[];
BEGIN
resultado = '{}';
FOR i IN 1..array_upper(a,1) LOOP
linha = '{}';
FOR j IN 1..array_upper(b,2) LOOP
val = 0;
FOR k IN 1..array_upper(a,2) LOOP
val = CAST(val + a[i][k]*b[k][j] AS FLOAT);
END LOOP;
linha = array_append(linha, val);
END LOOP;
resultado = array_cat(resultado,ARRAY[linha]);
END LOOP;
RETURN resultado;
END;
$$;

DROP FUNCTION public.projecao(anoMes INTEGER);
CREATE OR REPLACE FUNCTION public.projecao(anoMes INTEGER) RETURNS TABLE(ano_mes INTEGER, produto VARCHAR(60), projecao FLOAT)
LANGUAGE plpgsql
AS $$
DECLARE
x FLOAT[][];
xl FLOAT[][];
r FLOAT[][];
v FLOAT[];
proj FLOAT;
cur3 CURSOR IS SELECT DISTINCT venda.produto from venda;
BEGIN
FOR c IN cur3 LOOP
x = criax(c.produto);
xl = criaxl(x);
r = criar(c.produto);
v = resolve(multiplicaMatrizes(xl,x), multiplicaMatrizes(xl,r));
proj = (anoMes*v[1] + v[2]);
RETURN QUERY SELECT anoMes, c.produto, proj;
END LOOP;
RETURN;
END;
$$;


DROP FUNCTION IF EXISTS resolve(float[][], float[][]);
CREATE OR REPLACE FUNCTION resolve(matriz1 float[][], matriz2 float[][])
RETURNS float[] AS $$
DECLARE
    res float[] = ARRAY[0,0];
BEGIN
	res[1]=(matriz2[1][1]*matriz1[2][2]-matriz1[1][2]*matriz2[2][1])/(matriz1[1][1]*matriz1[2][2]-matriz1[1][2]*matriz1[2][1]);
    res[2]=(matriz1[1][1]*matriz2[2][1]-matriz2[1][1]*matriz1[2][1])/(matriz1[1][1]*matriz1[2][2]-matriz1[1][2]*matriz1[2][1]);
    RETURN res;
END;$$ LANGUAGE plpgsql;


SELECT public.projecao(201707);

