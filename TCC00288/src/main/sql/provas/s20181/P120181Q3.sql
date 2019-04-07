drop table if exists produto cascade;
create table produto(
id bigint not null,
nome varchar not null
);

drop table if exists venda cascade;
create table venda(
"data" timestamp not null,
produto bigint not null,
qtd integer not null
);


insert into produto
with
    recursive t1 as (select 1 as id union select t1.id+1 from t1 where t1.id<100)
select t1.id, format('produto %s',t1.id) from t1;

insert into venda
with
    recursive t1 as (select
                        1 as seq,
                        random() * (timestamp '2017-12-31 23:59:59.999999-03'-timestamp '2017-01-01 00:00:00.00-03')+timestamp '2017-01-01 00:00:00.00-03' as id,
                        random() * (100-1)+1 as produto,
                        random() * (10-1)+1 as qtd
                     union
                     select
                        seq+1 as seq,
                        random() * (timestamp '2017-12-31 23:59:59.999999-03'-timestamp '2017-01-01 00:00:00.00-03')+timestamp '2017-01-01 00:00:00.00-03' as id,
                        random() * (100-1)+1 as produto,
                        random() * (10-1)+1 as qtd
                     from t1 where seq < 10000)
select id, produto, qtd from t1;




drop function if exists best_sellers(d1 timestamp, d2 timestamp);
create or replace function best_sellers(d1 timestamp, d2 timestamp)
returns table(ano_mes bigint, lista varchar[][]) as $$
declare

begin
    return query
    with
        t1 as ( select
                    (extract(year from "data")*100 + extract(month from "data"))::bigint as anomes,
                    produto,
                    sum(qtd) as sum_qtd
                from venda
                where "data" between d1 and d2
                group by extract(year from "data")*100 + extract(month from "data"), 
                         produto),
        t2 as ( select
                    anomes,
                    avg(sum_qtd) as avg_qtd
                from t1
                group by anomes),
        t3 as ( select t1.anomes, t1.produto
                from t1 inner join t2 on t1.anomes = t2.anomes
                where t1.sum_qtd > t2.avg_qtd * 1.6)

    select anomes, array_agg(t4.nome)
    from t3 inner join produto t4 on t3.produto = t4.id
    group by anomes;


end;$$ language plpgsql;

select * from best_sellers(timestamp '2017-02-01', timestamp '2017-08-15');
