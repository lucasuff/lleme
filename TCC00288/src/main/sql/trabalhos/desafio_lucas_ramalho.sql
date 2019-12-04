DROP TABLE IF EXISTS public.venda;
CREATE TABLE public.venda (
    anoMes INTEGER,
    unidade INTEGER,
    vendedor VARCHAR(60),
    produto VARCHAR(60),
    valor FLOAT
);

INSERT INTO public.venda VALUES
    (201707, 5000, 'americanas', 'iphone', 45000.00),
    (201704, 25000, 'shoptime', 'frigideira', 49.90),
    (201707, 3000, 'submarino', 'iphone', 3999.00),
    (201708, 1000, 'sou barato', 'galaxy a30', 650.00);


CREATE OR REPLACE FUNCTION public.t1(produto VARCHAR(60)) RETURNS TABLE (anoMes INTEGER, valor FLOAT)
LANGUAGE plpgsql
AS $$
BEGIN
RETURN QUERY
SELECT venda.anoMes, sum(venda.valor) FROM venda
WHERE venda.produto = produto
GROUP BY venda.anoMes;
END;
$$;

CREATE OR REPLACE FUNCTION public.criax(produto VARCHAR(60)) RETURNS FLOAT[][]
LANGUAGE plpgsql
AS $$
DECLARE
x FLOAT[][];
auxX FLOAT[];
cur CURSOR IS SELECT anoMes FROM t1(produto);
BEGIN
x = '{}';
FOR c IN cur LOOP
auxX = ARRAY[cur.anoMes:FLOAT,1];
x = array_cat(x, auxX);
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
FOR c IN 1..array_length(x,2) LOOP
col = '{}';
FOR l IN 1..array_length(x,1) LOOP
col = array_append(col,x[l][c]);
END LOOP;
xl = array_cat(xl,col);
END LOOP;
RETURN xl;
END;
$$;

CREATE OR REPLACE FUNCTION public.criar(produto VARCHAR(60)) RETURNS FLOAT[][]
LANGUAGE plpgsql
AS $$
DECLARE
cur CURSOR IS SELECT valor FROM t1(produto);
r FLOAT[][];
BEGIN
FOR c IN cur LOOP
r = array_cat(r, ARRAY[c.valor]);
END LOOP;
RETURN r;
END;
$$;

CREATE OR REPLACE FUNCTION public.multiplicaMatrizes(a FLOAT[][], b FLOAT[][]) RETURNS FLOAT[][]
LANGUAGE plpgsql
AS $$
DECLARE
resultado FLOAT[][];
aux FLOAT[];
val FLOAT;
linha FLOAT[];
BEGIN
resultado = '{}';
FOR i IN 1..array_length(a,1) LOOP
linha = '{}';
FOR j IN 1..array_length(b,2) LOOP
val = 0;
FOR k IN 1..array_length(a,2) LOOP
val = val + a[i][k]*b[k][j];
END LOOP;
linha = array_append(linha, val);
END LOOP;
resultado = array_cat(resultado,ARRAY[linha]);
END LOOP;
RETURN resultado;
END;
$$;

CREATE OR REPLACE FUNCTION public.projecao(anoMes INTEGER) RETURNS TABLE(ano_mes INTEGER, produto VARCHAR(60), valor FLOAT)
LANGUAGE plpgsql
AS $$
DECLARE
x FLOAT[][];
xl FLOAT[][];
r FLOAT[][];
v FLOAT[];
proj FLOAT[];
cur CURSOR IS SELECT DISTINCT venda.produto from venda;
BEGIN
FOR c IN cur LOOP
x = criax(c.produto);
xl = criaxl(x);
r = criar(c.produto);
v = resolve(multiplicaMatrizes(xl,x), multiplicaMatrizes(xl,r));
proj = (anoMes*v[1] + v[2]);
RETURN QUERY SELECT c.anoMes as ano_mes, c.nome, c.proj as projecao;
END LOOP;
RETURN;
END;
$$;


