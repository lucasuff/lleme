DROP TABLE IF EXISTS public.vendas;
CREATE TABLE public.vendas (
    anomes integer,
    unidade integer,
    vendedor text,
    produto text,
    valor double precision
);

INSERT INTO public.vendas(anomes, unidade, vendedor, produto, valor) VALUES
	(201701, 1, 1, 'produto1', 100),
	(201702, 1, 1, 'produto1', 150),
	(201703, 1, 1, 'produto1', 200),
	(201704, 1, 1, 'produto1', 400),
	(201705, 1, 1, 'produto1', 450),
	(201706, 1, 1, 'produto1', 500);

CREATE OR REPLACE FUNCTION public.creater(nome text) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$
DECLARE
	aux1 double precision[];
	aux2 double precision[];
	line vendas.valor%TYPE;
BEGIN
	FOR line IN SELECT valor FROM createt1(nome) LOOP
		aux1 := array_append(aux1, line);
		aux2 := array_cat(aux2, array[aux1]);
		aux1 := ARRAY[]::double precision[];
	END LOOP;
	RETURN aux2;
END;
$$;


CREATE OR REPLACE FUNCTION public.createt1(nome text) RETURNS TABLE(anomes integer, valor double precision)
    LANGUAGE plpgsql
    AS $$
BEGIN
	RETURN QUERY SELECT vendas.anomes, sum(vendas.valor) FROM vendas WHERE vendas.produto = nome GROUP BY vendas.anomes;
END
$$;


CREATE OR REPLACE FUNCTION public.createx(nome text) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$
DECLARE
	line vendas.anoMes%TYPE;
	x integer[];
	aux integer[];
BEGIN
	FOR line IN SELECT * FROM createt1(nome) LOOP
		aux = array_append(aux, line);
		aux = array_append(aux, 1);
		x = array_cat(x, ARRAY[aux]);
		aux := ARRAY[]::integer[];
	END LOOP;
	RETURN x;
END;
$$;


CREATE OR REPLACE FUNCTION public.createxl(x integer[]) RETURNS integer[]
    LANGUAGE plpgsql
    AS $$
DECLARE
	aux1 integer[];
	aux2 integer[];
	aux3 integer[];
BEGIN
	FOR i IN array_lower(x, 1) .. array_upper(x, 1) LOOP
		aux1 := array_append(aux1, x[i][1]);
		aux2 := array_append(aux2, x[i][2]);
	END LOOP;
	aux3 = array_cat(aux1, array[aux2]);
	RETURN aux3;
END;
$$;

CREATE OR REPLACE FUNCTION public.multiply(a double precision[], b double precision[]) RETURNS double precision[]
    LANGUAGE plpgsql
    AS $$
DECLARE
	aux double precision;
	c double precision[2][2] = '{{0,0},{0,0}}';
BEGIN
	FOR i IN array_lower(a, 1) .. array_upper(a, 1) LOOP
		FOR j IN array_lower(b, 2) .. array_upper(b, 2) LOOP
			aux = 0;
			FOR k IN array_lower(a, 2) .. array_upper(a, 2) LOOP
				aux := aux + a[i][k] * b[k][j];
			END LOOP;
			c[i][j] = aux;
		END LOOP;
	END LOOP;
	RETURN c;
END;
$$;


CREATE OR REPLACE FUNCTION public.projection(anomes integer) RETURNS TABLE(anomes_projecao integer, produto text, valor double precision)
    LANGUAGE plpgsql
    AS $$
DECLARE
	x integer[];
	xl integer[];
	r double precision[];
	lhs double precision[];
	rhs double precision[];
	v double precision[];
	nome text;
BEGIN
	for nome in SELECT DISTINCT vendas.produto FROM vendas LOOP
		x = createX(nome);
		xl = createXL(x);
		r = createR(nome);
		lhs = multiply(xl, x);
		rhs = multiply(xl, r);

		v = resolver(lhs, rhs);
		RETURN QUERY SELECT anomes as ano_mes, nome as nome, (anomes*v[1] + v[2]) as projecao;
	END LOOP;
	
END;
$$;

DROP FUNCTION resolver(double precision[],double precision[]);
CREATE OR REPLACE FUNCTION public.resolver(lhs double precision[], rhs double precision[]) RETURNS double precision[]
    LANGUAGE plpgsql
    AS $$
DECLARE
	v1_num double precision;
	v2_num double precision;
	v1_den double precision;
	v2_den double precision;
	sol double precision[];
BEGIN
	v1_num = rhs[1][1]*lhs[2][2]-rhs[2][1]*lhs[1][2];
	v1_den = lhs[2][2]*lhs[1][1]-lhs[2][1]*lhs[1][2];
	v2_num = rhs[2][1]*lhs[1][1]-rhs[1][1]*lhs[2][1];
	v2_den = lhs[1][1]*lhs[2][2]-lhs[2][1]*lhs[1][2];
	sol[1] = v1_num/v1_den;
    sol[2] = v2_num/v2_den;
	RETURN sol;
END;
$$;

SELECT projection(201707);