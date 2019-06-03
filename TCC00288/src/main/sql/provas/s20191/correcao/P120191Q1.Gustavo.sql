drop table if exists operacao cascade;
create table operacao(
    acao varchar,
    "data" timestamp,
    qtd integer,
    preco float,
    primary key (acao,"data"),
    check (qtd != 0)
);

insert into operacao values('A1','2019-05-16'::timestamp,100,10.5);
insert into operacao values('A1','2019-05-18'::timestamp,-85,10.5);
insert into operacao values('A1','2019-05-19'::timestamp,100,10.5);
insert into operacao values('A2','2019-05-16'::timestamp,200,8.5);
insert into operacao values('A2','2019-05-17'::timestamp,50,9.);
insert into operacao values('A2','2019-05-18'::timestamp,150,19.);
insert into operacao values('A2','2019-05-20'::timestamp,-100,9.5);
insert into operacao values('A2','2019-05-21'::timestamp,-125,19.5);
insert into operacao values('A3','2019-05-16'::timestamp,300,7.5);


// aceitar implementacao prova
drop function if exists saldo(p_acoes varchar[]);
create or replace function saldo(acoes varchar[]) returns table(acao varchar, qtd integer, preco float) as $$
declare
	curs1 cursor(acao_in varchar) for select * from operacao where operacao.acao = acao_in order by data asc;
	curs2 cursor(acao_in varchar, dt timestamp) for select * from temporaria where temporaria.acao = acao_in and temporaria."data" < dt order by data asc;
	acao_arr varchar;
begin
	create temporary table if not exists temporaria(acao varchar, "data" timestamp, qtd integer, preco float) on commit delete rows;
	foreach acao_arr in array acoes loop
		for r1 in curs1(acao_arr) loop
			if r1.qtd > 0 then
				insert into temporaria values (r1.acao, r1.data, r1.qtd, r1.preco);
			else
				for r2 in curs2(r1.acao, r1."data") loop
					raise notice 'teste';
					if r2.qtd > abs(r1.qtd) then
						update temporaria set qtd = temporaria.qtd + r1.qtd where temporaria.acao = acao_arr and temporaria."data" = r2.data;
						exit;
					else
						r1.qtd = r1.qtd + r2.qtd;
						delete from temporaria where temporaria.acao = acao_arr and temporaria.data = r2.data;
					end if;
				end loop;
			end if;
		end loop;
	end loop;
	delete from temporaria where temporaria.qtd = 0;
	return query select temporaria.acao, sum(temporaria.qtd)::integer, temporaria.preco from temporaria GROUP BY temporaria.acao, temporaria.preco;
end;
$$ language plpgsql;

select * from saldo('{"A1","A2","A3"}') order by 1;
