reset role;

drop table if exists departamento cascade;
create table departamento(
    codigo integer not null,
    nome varchar not null,
    primary key (codigo)
);

drop table if exists funcionario cascade;
create table funcionario(
    cpf integer not null,
    nome varchar not null,
    endereco varchar not null,
    departamento integer not null,
    salario float not null,
    primary key (cpf),
    foreign key (departamento) references departamento(codigo),
    check (salario > 0)
);
ALTER TABLE funcionario ENABLE ROW LEVEL SECURITY;


insert into departamento values (1,'engenharia');
insert into departamento values (2,'comercial');
insert into departamento values (3,'juridico');

insert into funcionario values (1,'N1','E1',1,100.);
insert into funcionario values (2,'N2','E2',2,100.);
insert into funcionario values (3,'N3','E3',3,300.);

---------------------------------------------------------------------------------
---------------------------------------------------------------------------------
---------------------------------------------------------------------------------
drop table if exists depto_role cascade;
create table depto_role(
    departamento integer not null,
    "role" oid not null,
    primary key (departamento,"role"),
    foreign key (departamento) references departamento(codigo) on update cascade
);

revoke all on pg_catalog.pg_authid from pessoal;
revoke all on pg_catalog.pg_authid from comercial;
revoke all on pg_catalog.pg_authid from juridico;
drop role if exists user1, user2, user3, user4, pessoal, comercial, juridico, gerente;
create role pessoal;
create role comercial;
create role juridico;
create role gerente;
grant all privileges on funcionario to pessoal;
grant all privileges on funcionario to comercial;
grant all privileges on funcionario to juridico;
grant SELECT on depto_role to pessoal;
grant SELECT on depto_role to comercial;
grant SELECT on depto_role to juridico;
grant SELECT on pg_catalog.pg_authid to pessoal;
grant SELECT on pg_catalog.pg_authid to comercial;
grant SELECT on pg_catalog.pg_authid to juridico;
create role user1 LOGIN INHERIT;
create role user2 LOGIN INHERIT;
create role user3 LOGIN INHERIT;
create role user4 LOGIN INHERIT;
grant comercial to user1,user2;
grant gerente to user1;
grant pessoal to user3;
grant juridico to user4;


insert into depto_role values (1,(select oid from pg_catalog.pg_authid where rolname = 'pessoal'));
insert into depto_role values (2,(select oid from pg_catalog.pg_authid where rolname = 'comercial'));
insert into depto_role values (3,(select oid from pg_catalog.pg_authid where rolname = 'juridico'));

CREATE POLICY regra_funcionario1 ON funcionario to juridico
    using (departamento in
            (select departamento
             from depto_role
             where "role" = (select oid
                             from pg_catalog.pg_authid
                             where rolname = 'juridico')))
    WITH CHECK (false);
CREATE POLICY regra_funcionario2 ON funcionario to pessoal using (departamento in (select departamento from depto_role where "role" = (select oid from pg_catalog.pg_authid where rolname = 'pessoal'))) WITH CHECK (false);
CREATE POLICY regra_funcionario3 ON funcionario to comercial using (departamento in (select departamento from depto_role where "role" = (select oid from pg_catalog.pg_authid where rolname = 'comercial'))) WITH CHECK (false);
CREATE POLICY regra_funcionario4 ON funcionario to gerente WITH CHECK (true);
CREATE POLICY regra_funcionario5 ON funcionario to public WITH CHECK (false);

set role user1;
update funcionario set salario = salario * 1.1;

set role user2;
update funcionario set salario = salario * 1.1;

reset role;
select * from funcionario;

