drop table if exists produto cascade;
create table produto(
    codigo varchar,
    descricao varchar,
    preco float,
    primary key (codigo),
    check (preco >0)
);

insert into produto values('P1','P1',10.);
insert into produto values('P2','P2',20.);
insert into produto values('P3','P3',30.);
insert into produto values('P4','P4',40.);
insert into produto values('P5','P5',50.);
insert into produto values('P6','P6',60.);
insert into produto values('P7','P7',70.);
insert into produto values('P8','P8',80.);
insert into produto values('P9','P9',90.);


drop function if exists totalizar(p_produtotalizartos varchar[], p_qtds integer[]);
create or replace function totalizar(p_produtos varchar[], p_qtds integer[]) returns float as $$
declare
    v_total float = 0;
begin
    with
        t2 as (SELECT t.* from unnest(p_produtos,p_qtds) as t(codigo,qtd))
    select sum(t1.preco * t2.qtd) as total
    into v_total
    from produto as t1 natural join t2;
    return v_total;
end;$$ language plpgsql;

select * from totalizar('{"P1","P5"}','{3,5}') order by 1;
