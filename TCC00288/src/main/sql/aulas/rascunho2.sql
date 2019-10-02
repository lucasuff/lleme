drop table if exists artista cascade;
create table artista (
  id int,
  CONSTRAINT artista_pk PRIMARY KEY
  (id));

drop table if exists arena cascade;
create table arena (
  id int,
  CONSTRAINT arena_pk PRIMARY KEY
  (id));

drop table if exists concerto cascade;
create table concerto (
  artista int,
  arena int,
  "data" date,
  preco float,
  CONSTRAINT concerto_pk PRIMARY KEY
  (artista,arena),
  CONSTRAINT concerto_artista_fk
  FOREIGN KEY (artista) REFERENCES
  artista(id),
  CONSTRAINT conereto_arena_fk FOREIGN
  KEY (arena) REFERENCES arena(id));

insert into artista values (1);
insert into artista values (2);
insert into artista values (3);

insert into arena values (1);

insert into concerto values (1,1,'2019-09-28'::date,500.0);

drop function if exists adicionar (int, int, date, float);

create or replace function adicionar(pArtista int, pArena int, pData date, pPreco float)
returns void as $$

declare

begin

if not exists (select artista
               from concerto
               where pArena = concerto.arena and pData = concerto."data") then

    insert into concerto values (pArtista, pArena, pData, pPreco);

else
    raise exception 'Concerto já existe';

end if;

end;

$$ language plpgsql;

select adicionar(2,1,'2019-09-29'::date,100.0);
select * from concerto;