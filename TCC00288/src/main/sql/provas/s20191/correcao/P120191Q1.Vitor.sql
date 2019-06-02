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


drop function if exists saldo(p_acoes varchar[]);
CREATE OR REPLACE FUNCTION saldo(acoes varchar[]) RETURNS TABLE (acao varchar, qtd integer, preco float) AS $$
DECLARE
	cur1 CURSOR FOR(SELECT t.* FROM unnest(acoes) AS t(acao));
	qtdVenda integer;
	r2 RECORD;
BEGIN
	DROP TABLE IF EXISTS "temp";
	CREATE TEMP TABLE "temp"(
		acao varchar,
		qtd integer,
		preco float);

	FOR r1 in cur1 LOOP
		INSERT INTO "temp"
		SELECT t10.acao, t10.qtd, t10.preco FROM operacao AS t10
					WHERE t10.acao = r1.acao AND t10.qtd > 0
					GROUP BY t10.acao, t10.preco, t10.qtd, t10."data"
					ORDER BY t10."data" ASC;

		SELECT sum(t11.qtd) INTO qtdVenda FROM operacao AS t11
			WHERE t11.acao = r1.acao AND t11.qtd < 0;

		FOR r2 IN SELECT * FROM "temp" LOOP
			IF(qtdVenda<0) THEN
				qtdVenda = qtdVenda + r2.qtd;
					if(QtdVenda<0) THEN
						UPDATE "temp" SET qtd = 0 WHERE "temp".acao = r2.acao AND "temp".qtd = r2.qtd AND "temp".preco = r2.preco;
					else
						UPDATE "temp" SET qtd = qtdVenda WHERE "temp".acao = r2.acao AND "temp".qtd = r2.qtd AND "temp".preco = r2.preco;
					END IF;
			END IF;
		END LOOP;

		return query select * from "temp";

		DELETE FROM "temp";
	END LOOP;

END; $$ language plpgsql;

select * from saldo('{"A1","A2","A3"}') order by 1;
