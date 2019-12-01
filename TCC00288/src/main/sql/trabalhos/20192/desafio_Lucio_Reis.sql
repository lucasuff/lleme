DROP TABLE IF EXISTS Venda;
CREATE TABLE Venda(
	ano_mes integer,
	unidade integer,
	vendedor integer,
	produto integer,
	valor float);
	
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201704, 1, 1, 1, 900.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201705, 1, 1, 1, 900.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201705, 1, 1, 1, 900.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201706, 1, 1, 1, 900.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201706, 1, 1, 1, 900.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201706, 1, 1, 1, 900.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201705, 1, 1, 2, 300.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201705, 1, 1, 2, 300.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201706, 1, 1, 2, 300.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201706, 1, 1, 2, 300.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201704, 1, 1, 3, 400.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201705, 1, 1, 3, 400.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201706, 1, 1, 3, 400.00);
INSERT INTO Venda(ano_mes, unidade, vendedor, produto, valor) Values(201706, 1, 1, 3, 400.00);


DROP FUNCTION main(integer);
CREATE OR REPLACE FUNCTION main(anomes integer) RETURNS TABLE(am integer, pp integer, vp float) AS $$
DECLARE
	produtos integer[];
	prod integer;
	
	x_array integer[][];
	x_tmp integer[];
	
	tl_x integer[][];
	tl_x_tmp integer[];
	
	rec record;
	
	r_tmp float[];
	r_array float[][];
	
	c1 float[][];
	c2 float[][];
	coef float[];
	
	projec float := 0;
	
BEGIN
	DROP TABLE IF EXISTS projecoes;
	CREATE TABLE projecoes(anomes_projec integer, 
						   produto_projec integer, 
						   valor_projec float);
	
	SELECT array_agg(distinct(produto)) INTO produtos FROM Venda;
	FOREACH prod IN ARRAY produtos LOOP
		
		x_array = '{}';
		tl_x = '{}';
		r_array = '{}';
		RAISE NOTICE 'Computando projeçao para o produto %', prod;
		FOR rec IN (SELECT ano_mes, sum(valor) FROM Venda GROUP BY produto, ano_mes HAVING produto = prod) LOOP
			RAISE NOTICE 'ok';
			x_tmp = ARRAY[rec.ano_mes,1];
			x_array = array_cat(x_array, ARRAY[x_tmp]);
		END LOOP;
		
		
		FOR i IN 1..array_length(x_array,2)LOOP
			tl_x_tmp = '{}';
			FOR j IN 1..array_length(x_array,1)LOOP
				tl_x_tmp = array_append(tl_x_tmp, x_array[j][i]);
			END LOOP;
        	tl_x = array_cat(tl_x, ARRAY[tl_x_tmp]);
	    END LOOP;
		
		FOR rec IN (SELECT ano_mes, sum(valor) as valor FROM Venda GROUP BY produto, ano_mes HAVING produto = prod) LOOP
			RAISE NOTICE 'ok';
			r_tmp = ARRAY[rec.valor];
			r_array = array_cat(r_array, ARRAY[r_tmp]);
		END LOOP;
		
		c1 = mult(tl_x,x_array);
		c2 = mult(tl_x,r_array);
		coef = resolver(c1,c2);
		
		projec = anomes * coef[1] + coef[2];
		
		INSERT INTO projecoes(anomes_projec, produto_projec, valor_projec) VALUES(anomes, prod, projec);
	END LOOP;
	RETURN QUERY SELECT * FROM projecoes;
END; $$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS mult(float[][],float[][]);
CREATE OR REPLACE FUNCTION mult(m1 float[][], m2 float[][])
RETURNS float[][] as $$
DECLARE
	 cel float;
	 line float[];
	 
	 response float[][];
BEGIN
	 IF (array_length(m2,1) <> array_length(m1,2)) THEN
	 	RAISE EXCEPTION 'error';
	 END IF;
	 response='{}';
	 FOR i IN 1..array_length(m1,1) LOOP
	 	line = '{}';
	 	FOR j IN 1..array_length(m2,2) LOOP
	 		cel = 0;
	 		FOR k IN 1..array_length(m1,2) LOOP
	 			cel = cel + m1[i][k]*m2[k][j];
	 		END LOOP;
			line = array_append(line,cel);
	 	END LOOP;
		
	 	response = array_cat(response,array[line]);
	 END LOOP;
	 RETURN response;
END;$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS resolver(float[][], float[][]);
CREATE OR REPLACE FUNCTION resolver(m1 float[][], m2 float[][])
RETURNS float[] AS $$
DECLARE
    sol float[] = '{0.,0.}';
BEGIN
	sol[1]=(m2[1][1]*m1[2][2]-m1[1][2]*m2[2][1])/(m1[1][1]*m1[2][2]-m1[1][2]*m1[2][1]);
    sol[2]=(m1[1][1]*m2[2][1]-m2[1][1]*m1[2][1])/(m1[1][1]*m1[2][2]-m1[1][2]*m1[2][1]);
    RETURN sol;
END;$$ LANGUAGE plpgsql;

SELECT main(201707);
