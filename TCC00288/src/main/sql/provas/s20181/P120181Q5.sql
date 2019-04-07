drop table if exists prato cascade;
create table prato(
id int,
nome varchar
);
drop table if exists ingrediente cascade;
create table ingrediente(
id int,
nome varchar,
estoque int,
qtd_por_emb float,
);
drop table if exists prato_ingrediente cascade;
create table prato_ingrediente(
prato int,
ingrediente varchar,
quantidade float
);
drop table if exists cardapio cascade;
create table cardapio(
"data" date,
almoco int,
jantar int
);


insert into ingrediente values (1,'ovos',1,12.);
insert into ingrediente values (2,'acucar',1,1.0);
insert into ingrediente values (3,'farinha',1,1.0);
insert into ingrediente values (4,'manteiga',1,500.0);
insert into ingrediente values (5,'cebola',10,1.0);
insert into ingrediente values (6,'queijo',1,500.);

insert into prato values (1,'prato 1');
insert into prato values (2,'prato 2');

insert into prato_ingrediente values (1,1,2);
insert into prato_ingrediente values (1,2,0.2);
insert into prato_ingrediente values (1,3,0.5);
insert into prato_ingrediente values (1,4,0.5);
insert into prato_ingrediente values (2,1,4);
insert into prato_ingrediente values (2,5,1);
insert into prato_ingrediente values (2,6,0.1);



drop function if exists lista_de_compras(d1 date, d2 date);
create or replace function lista_de_compras(d1 date, d2 date)
returns table(caminho integer[], distancia integer) as $$
declare

begin
    with
        t1 as (select t2.ingrediente, sum(t2.quantidade) as quantidade
               from cardapio t1
               inner join prato_ingrediente t2 on t1.almoco = t2.almoco
               where t1."data" between d1 and d2
               group by t2.ingrediente
               union
               select t2.ingrediente, sum(t2.quantidade) as quantidade
               from cardapio t1
               inner join prato_ingrediente t2 on t1.almoco = t2.jantar
               where t1."data" between d1 and d2
               group by t2.ingrediente)



end;$$ language plpgsql;

--select * from menor_caminho(2, 10);
