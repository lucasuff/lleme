drop table if exists cliente cascade;
create table cliente(
id int primary key,
nome varchar not null);



drop table if exists conta_corrente cascade;
create table conta_corrente(
id int primary key,
abertura timestamp not null,
encerramento timestamp);



drop table if exists correntista cascade;
create table correntista(
cliente int references cliente(id),
conta_corrente int references conta_corrente(id),
primary key(cliente, conta_corrente));



drop table if exists limite_credito cascade;
create table limite_credito(
conta_corrente int references conta_corrente(id),
valor float not null,
inicio timestamp not null,
fim timestamp);



drop table if exists movimento cascade;
create table movimento(
conta_corrente int references conta_corrente(id),
"data" timestamp,
valor float not null,
primary key (conta_corrente,"data"));



-- Triggers em movimento
drop function if exists create_temp_table() cascade;
create or replace function create_temp_table() returns trigger as $$
declare
begin
    drop table if exists conta_afetada cascade;
    create temporary table conta_afetada(id int) on commit drop;
    return null;
end;$$ language plpgsql;
create trigger trigger_create_temp_table before insert or update or delete on movimento
for each statement execute procedure create_temp_table();

create trigger trigger_create_temp_table2 before insert or update or delete on limite_credito
for each statement execute procedure create_temp_table();

--
drop function if exists salva_conta_atefada() cascade;
create or replace function salva_conta_atefada() returns trigger as $$
declare
begin
    if (tg_op = 'DELETE' or tg_op = 'UPDATE') then
        insert into conta_afetada values(old.conta_corrente);
    end if;
    if (tg_op = 'INSERT' or tg_op = 'UPDATE') then
        insert into conta_afetada values(new.conta_corrente);
        return new;
    end if;
    return old;
end;$$ language plpgsql;
create trigger trigger_salva_conta_atefada before insert or update or delete on movimento
for each row execute procedure salva_conta_atefada();

--
drop function if exists check_limite() cascade;
create or replace function check_limite() returns trigger as $$
declare
begin
    if exists (with
                    t1 as (select distinct id from conta_afetada),
                    t2 as (select distinct conta_corrente as id, "data" from movimento where conta_corrente in (select id from t1)),
                    t3 as (select t2.id, t2."data", sum(m.valor) saldo
                           from t2 inner join movimento m on t2.id=m.conta_corrente and t2."data">=m."data"
                           group by t2.id, t2."data"
                           having sum(m.valor)<0),
                    t4 as (select id, abertura as d from conta_corrente where id in (1,2)
                           union select id, coalesce(encerramento,timestamp 'infinity') from conta_corrente where id in (select id from t1)
                           union select conta_corrente, inicio from limite_credito where conta_corrente in (select id from t1)
                           union select conta_corrente, coalesce(fim,timestamp 'infinity') from limite_credito where conta_corrente in (select id from t1)),
                    t5 as (select t41.id, t41.d as inicio, (select min(t42.d) from t4 as t42 where t42.id=t41.id and t42.d>t41.d) as fim
                           from t4 t41
                           where (select min(t42.d) from t4 as t42 where t42.id=t41.id and t42.d>t41.d) is not null),
                    t6 as (select t5.id, t5.inicio, t5.fim, coalesce(t51.valor,0) limite
                           from t5 left join limite_credito t51 on t5.id=t51.conta_corrente and t5.inicio=t51.inicio and coalesce(t5.fim,timestamp 'infinity')=t5.fim)
               select *
               from t3 inner join t6 on t3.id = t6.id and t3."data" between t6.inicio and t6.fim
               where t3.saldo < -t6.limite
               limit 1) then
        raise exception 'erro: violocao de limite de credito';
    end if;
    return null;
end;$$ language plpgsql;

--
drop function if exists check_limite2() cascade;
create or replace function check_limite2() returns trigger as $$
declare
    afetadas cursor for select distinct id from conta_afetada;
    movimentos cursor(id integer) for select "data", valor from movimento where conta_corrente = id order by 1;
    saldo integer;
    data_anterior date;
begin
    for r1 in afetadas loop
        saldo = 0;
        data_anterior = null;
        for r2 in movimentos(r1.id) loop

            if (r2."data"::date > data_anterior and saldo < 0) then
                if (not exists (select 1
                               from limite_credito
                               where conta_corrente = r1.id
                                     and data_anterior between inicio and coalesce(fim,'infinity'::timestamp)
                                     and valor >= -saldo)) then
                    raise exception 'Erro: violocao de limite de credito';
                end if;
            end if;
            saldo = saldo + r2.valor;
            data_anterior = r2."data"::date;

        end loop;

        if (data_anterior is not null) then
            if (not exists (select 1
                            from limite_credito
                            where conta_corrente = r1.id
                                  and data_anterior between inicio and coalesce(fim,'infinity'::timestamp)
                                  and valor >= -saldo)) then
                raise exception 'Erro: violocao de limite de credito';
            end if;
        end if;

    end loop;
    return null;
end;$$ language plpgsql;

create trigger trigger_check_limite after insert or update or delete on movimento
for each statement execute procedure check_limite2();


--Triggers em limite_credito
drop trigger if exists trigger_create_temp_table on limite_credito;
create trigger trigger_create_temp_table after insert or update or delete on limite_credito
for each statement execute procedure create_temp_table();

--
drop trigger if exists trigger_salva_conta_atefada on limite_credito;
create trigger trigger_salva_conta_atefada before insert or update or delete on limite_credito
for each row execute procedure salva_conta_atefada();

--
drop trigger if exists trigger_check_movimento on limite_credito;
create trigger trigger_check_movimento after insert or update or delete on limite_credito
for each row execute procedure check_limite2();






-- Dados
insert into cliente values(1,'pessoa 1');
insert into cliente values(2,'pessoa 2');
insert into cliente values(3,'pessoa 3');

insert into conta_corrente values(1,timestamp '2017-05-13 8:00:00.0-03');
insert into conta_corrente values(2,timestamp '2017-04-23 15:00:00.0-03');

insert into correntista values(1,1);
insert into correntista values(2,1);
insert into correntista values(3,2);

insert into limite_credito select 1,1000,abertura,abertura + interval '3 months' from conta_corrente where id=1;
insert into limite_credito select 1,500,abertura + interval '5 months' from conta_corrente where id=1;

insert into limite_credito select 2,200,abertura,abertura + interval '4 months' from conta_corrente where id=2;
insert into limite_credito select 2,500,abertura + interval '7 months' from conta_corrente where id=1;


delete from movimento;
insert into movimento
select 1,abertura + interval '10 days',1000 from conta_corrente where id=1
union select 1,abertura + interval '15 days',-500 from conta_corrente where id=1
union select 1,abertura + interval '20 days',-500 from conta_corrente where id=1;

insert into movimento
select 1,abertura + interval '25 days',-500 from conta_corrente where id=1
union select 1,abertura + interval '26 days',-800 from conta_corrente where id=1;


insert into movimento
select 2,abertura + interval '15 days',1000 from conta_corrente where id=2
union select 2,abertura + interval '20 days',-500 from conta_corrente where id=2
union select 2,abertura + interval '25 days',-500 from conta_corrente where id=2;
insert into movimento select 2,abertura + interval '30 days',-100 from conta_corrente where id=2;


select * from movimento;

--update limite_credito set valor=10 where conta_corrente = 1 and inicio=(select abertura from conta_corrente where id=1);


--select * from movimento;