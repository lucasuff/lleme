drop table if exists prova cascade;
create table prova(
    disciplina varchar not null,
    semestre int not null,
    turma varchar not null,
    prova int not null,
    sigla varchar not null,
    obrigatoria boolean not null,
    primary key (disciplina, semestre, turma, prova),
    unique (disciplina, semestre, turma, sigla)
);

drop table if exists nota cascade;
create table nota(
    disciplina varchar not null,
    semestre int not null,
    turma varchar not null,
    aluno int not null,
    prova int not null,
    nota float not null,
    primary key (disciplina, semestre, turma, aluno, prova),
    foreign key (disciplina, semestre, turma, prova) references prova
);

insert into prova values ('TCC00288', 20192, 'A1', 1, 'P1', true);
insert into prova values ('TCC00288', 20192, 'A1', 2, 'P2', true);
insert into prova values ('TCC00288', 20192, 'A1', 3, 'VR', false);
insert into prova values ('TCC00288', 20192, 'A1', 4, 'VS', false);

insert into nota values ('TCC00288', 20192, 'A1', 1, 1, 7.0);
insert into nota values ('TCC00288', 20192, 'A1', 1, 3, 7.0);

drop function if exists notas() cascade;
create or replace function notas() returns
table(
    disciplina varchar,
    semestre int,
    turma varchar,
    aluno int,
    notas float[]
)
as $$
declare
    l int;
    opcoes boolean[];
    notas float[];

    c_turmas cursor for
        select distinct t1.disciplina, t1.semestre, t1.turma from nota t1;
    c_alunos cursor(disc varchar, sem int, tur varchar) for
        select distinct t1.aluno
        from nota t1
        where t1.disciplina = disc
            and t1.semestre = sem
            and t1.turma = tur;
    c_notas cursor(disc varchar, sem int, tur varchar, mat int) for
        select t1.prova, t1.nota
        from nota t1
        where t1.disciplina = disc
            and t1.semestre = sem
            and t1.turma = tur
            and t1.aluno = mat;
    r1 record;
begin
    for r1 in c_turmas loop

        with
            tmp as (select t1.prova, t1.obrigatoria
                    from prova t1
                    where t1.disciplina = r1.disciplina
                        and t1.semestre = r1.semestre
                        and t1.turma = r1.turma
                    order by t1.prova)
        select array_agg(obrigatoria) into opcoes from tmp;

        for r2 in c_alunos(r1.disciplina, r1.semestre, r1.turma) loop
            notas = '{}';

            l = array_length(opcoes,1);
            for i in 1..l loop
                if opcoes[i] then
                    notas = array_append(notas,0.0::float);
                else
                    notas = array_append(notas,null);
                end if;
            end loop;

            for r3 in c_notas(r1.disciplina, r1.semestre, r1.turma, r2.aluno) loop
                notas[r3.prova] = r3.nota;
            end loop;

            return query select r1.disciplina, r1.semestre, r1.turma, r2.aluno, notas;

        end loop;
    end loop;
end
$$ language plpgsql;

select * from notas();