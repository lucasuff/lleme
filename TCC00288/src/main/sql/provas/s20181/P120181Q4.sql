drop table if exists cliente cascade;
create table cliente(
id bigint primary key,
titular bigint references cliente(id),
nome varchar not null
);
CREATE INDEX ON cliente (titular);

drop table if exists procedimento cascade;
create table procedimento(
id bigint primary key,
nome varchar not null
);

drop table if exists atendimento cascade;
create table atendimento(
id bigint primary key,
"data" timestamp not null,
procedimento bigint references procedimento(id) not null,
cliente bigint not null
);
CREATE INDEX ON atendimento (cliente,"data");

insert into cliente
with recursive t1 as (select 1 as id union select t1.id+1 from t1 where t1.id < 1000)
select t1.id, random()*(100-1)+1,format('cliente %s',t1.id) from t1;

insert into procedimento
with recursive t1 as (select 1 as id union select t1.id+1 from t1 where t1.id < 10000)
select t1.id, format('procedimento %s',t1.id) from t1;

insert into atendimento
with recursive t1 as (select 1 as id union select t1.id+1 from t1 where t1.id < 100000)
select
    id,
    random() * (timestamp '2017-12-31 23:59:59.999999-03'-timestamp '2017-01-01 00:00:00.00-03')+timestamp '2017-01-01 00:00:00.00-03',
    random()*(10000-1)+1,
    random()*(1000-1)+1 from t1;


drop function if exists consolidar();
create or replace function consolidar() returns void as $$
declare
    qtd_vidas_contrato bigint;
    qtd_atend_urgencia int;
    csr1 cursor for select * from atendimento;
    csr2 cursor(c bigint, d timestamp) for select * from atendimento 
    where cliente=c and "data" < d;
begin
    drop table if exists fato cascade;
    create table fato(
        id bigint not null,
        "data" timestamp not null,
        procedimento bigint not null,
        qtd_vidas_contrato int not null,
        qtd_atend_urgencia int not null
        );

    for r1 in csr1 loop
        select count(*) into qtd_vidas_contrato
        from cliente t1
        where t1.id = r1.cliente
              or t1.titular = (select titular from cliente where id = r1.cliente);

        qtd_atend_urgencia = 0;
        for r2 in csr2(r1.cliente,r1."data") loop
            if (extract(hour from r2."data") > 22) then
                qtd_atend_urgencia = qtd_atend_urgencia + 1;
            end if;
        end loop;

        insert into fato values (r1.id,r1."data",r1.procedimento,qtd_vidas_contrato,qtd_atend_urgencia);
    end loop;
end;$$ language plpgsql;

select consolidar();
select * from fato;