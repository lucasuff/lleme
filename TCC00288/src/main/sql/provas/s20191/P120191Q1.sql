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
create or replace function saldo(p_acoes varchar[])
returns table (acao varchar, qtd integer, preco float) as $$
declare
    a varchar;
    v_saldo integer;
    v_preco float;
    r1 operacao%rowtype;
    r2 operacao%rowtype;
    c_compras cursor(v_acao varchar) for
        select * from operacao as t1 where t1.acao = v_acao and t1.qtd > 0 order by "data";
    c_vendas cursor(v_acao varchar) for
        select * from operacao as t1 where t1.acao = v_acao and t1.qtd < 0 order by "data";
begin
    create local temp table saldo (acao varchar, qtd integer, preco float) on commit drop;

    foreach a in array p_acoes loop
        open c_compras(a);
        open c_vendas(a);

        v_saldo = 0;
        v_preco = 0;
        loop
            if v_saldo <= 0 then
                fetch c_compras into r1;
                exit when not found;
                v_saldo = v_saldo + r1.qtd;
                v_preco = r1.preco;
            end if;


            fetch c_vendas into r2;
            if not found then
                insert into saldo select a, v_saldo, v_preco;
                fetch c_compras into r1;
                exit when not found;
                v_saldo = r1.qtd;
                v_preco = r1.preco;
            else
                v_saldo = v_saldo + r2.qtd;
            end if;
        end loop;

        close c_compras;
        close c_vendas;

    end loop;
    return query select t1.acao, sum(t1.qtd)::integer, t1.preco from saldo as t1 group by t1.acao, t1.preco;
end;$$ language plpgsql;

select * from saldo('{"A1","A2","A3"}') order by 1;
