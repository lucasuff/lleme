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
CREATE OR REPLACE FUNCTION saldo(acoes varchar[]) RETURNS TABLE(acName varchar, qt integer, prec float) AS $$
DECLARE
    curSaldo cursor(acao1 varchar) FOR SELECT * FROM operacao WHERE acao = acao1;
    tam integer;
    qtde integer;
    sel integer;
    acao1 varchar;
    sele operacao.qtd%TYPE;
BEGIN
    tam = array_length(acoes,1);
    FOREACH acao1 in ARRAY $1 loop
   	 raise notice '%', acao1;
   	 FOR r in curSaldo(acao1) loop
   		 SELECT qtd into sele from acao WHERE acaoName = r.acao and preco=r.preco;
   		 if not found then
   			 INSERT INTO acao VALUES(r.acao, r.qtd, r.preco);
   		 else
   			 UPDATE acao SET qtd=qtd+r.qtd WHERE acaoName = acao1 and preco = r.preco;
   		 end if;
   	 end loop;
    end loop;
   	 return query SELECT * FROM acao;
END;
$$ LANGUAGE plpgsql;


select * from saldo('{"A1","A2"}') order by 1;
