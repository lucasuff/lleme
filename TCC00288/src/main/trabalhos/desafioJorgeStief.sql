drop table if exists venda cascade ;
create table venda(
    ano_mes int not null,
    unidade int,
    vendedor int,
    produto int,
    valor float
);


insert into venda values(1,1,1,10,100.0);
insert into venda values(1,1,2,3,200.0);
insert into venda values(1,1,3,10,300.0);
insert into venda values(2,1,1,5,200.0);
insert into venda values(2,1,2,5,300.0);
insert into venda values(2,1,3,10,500.0);
insert into venda values(3,1,1,1,900.0);
insert into venda values(3,1,2,2,200.0);
insert into venda values(3,1,3,10,500.0);
insert into venda values(4,1,1,10,200.0);
insert into venda values(4,1,2,10,150.0);
insert into venda values(4,1,3,10,500.0);
insert into venda values(5,1,1,10,500.0);
insert into venda values(5,1,2,10,300.0);
insert into venda values(5,1,3,10,700.0);
insert into venda values(6,1,1,10,200.0);
insert into venda values(6,1,2,10,200.0);
insert into venda values(6,1,3,10,200.0);



drop function if exists transpor(float[][]);
create or replace function transpor(matriz float[][]) returns
float[][] as $$
declare
	 i int;
	 j int;
	 linhas int;
	 colunas int;
	 linha float[];
	 resultado float[][];
begin
	 linhas = array_length(matriz,1);
	 colunas = array_length(matriz,2);
	 resultado='{}';
	 for j in 1..colunas loop
	 	linha = '{}';
	 	for i in 1..linhas loop
	 		linha = array_append(linha,matriz[i][j]);
	 	end loop;
	 	resultado = array_cat(resultado,array[linha]);
	 end loop;
	 return resultado;
end;$$ language plpgsql;


drop function if exists multiplicar(float[][],float[][]);
create or replace function multiplicar(ma float[][], mb float[][])
returns float[][] as $$
declare
	 i int;
	 j int;
	 ma_linhas int;
	 ma_colunas int;
	 mb_linhas int;
	 mb_colunas int;
	 celula float;
	 linha float[];
	 resultado float[][];
begin
	 ma_linhas = array_length(ma,1);
	 ma_colunas = array_length(ma,2);
	 mb_linhas = array_length(mb,1);
	 mb_colunas = array_length(mb,2);
	 if (ma_colunas <> mb_linhas) then
	 	raise exception 'matrizes inconpativeis';
	 end if;
	 resultado='{}';
	 for i in 1..ma_linhas loop
	 	linha = '{}';
	 	for j in 1..mb_colunas loop
	 		celula = 0;
	 		for kk in 1..ma_colunas loop
	 			celula = celula + ma[i][kk]*mb[kk][j];
	 		end loop;
			linha = array_append(linha,celula);
	 	end loop;
	 	resultado = array_cat(resultado,array[linha]);
	 end loop;
	 return resultado;
end;$$ language plpgsql;

drop function if exists resolver(float[][], float[][]);
create or replace function resolver(m1 float[][], m2 float[][])
returns float[] as $$
declare
    sol float[] = '{0.,0.}';
begin
	raise notice 'm1 = %,  m2 = %', m1, m2;
    sol[1]=(m2[1][1]*m1[2][2]-m1[1][2]*m2[2][1])/(m1[1][1]*m1[2][2]-m1[1][2]*m1[2][1]);
    sol[2]=(m1[1][1]*m2[2][1]-m2[1][1]*m1[2][1])/(m1[1][1]*m1[2][2]-m1[1][2]*m1[2][1]);
	raise notice 'coefs = %', sol;
    return sol;
end;$$ language plpgsql;



drop function if exists projecao(int);
create or replace function  projecao(p_ano_mes int) returns
 table(produto int, previsao float) as $$
declare
 x float[][];
 xl float[][];
 r float[][];
 c1 float[][];
 c2 float[][];
 coef float[];
 prod int;
begin
 for prod in select distinct t1.produto from venda t1 loop
	 execute 'with
		t1 as (select ano_mes, sum(valor) as valor
		from venda group by ano_mes, produto)
	 select 
		array_agg(array[ano_mes,1]),
		transpor(array_agg(array[t1.ano_mes,1])),
		transpor(array[array_agg(t1.valor)]) 
	 from t1'
	 into x,xl,r;
	 c1 = multiplicar(xl,x);
	 c2 = multiplicar(xl,r);
	 coef = resolver(c1,c2);
	 produto = prod;
	 previsao= coef[1] * p_ano_mes + coef[2];
	 return next;  
 end loop;
end$$ language plpgsql;

select * from projecao(201702);
