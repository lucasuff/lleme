DROP TABLE IF EXISTS departamento CASCADE;
DROP TABLE IF EXISTS professor CASCADE;
DROP TABLE IF EXISTS disciplina CASCADE;
DROP TABLE IF EXISTS turma CASCADE;
DROP TABLE IF EXISTS curso CASCADE;
DROP TABLE IF EXISTS aluno CASCADE;
DROP TABLE IF EXISTS cursa CASCADE;
DROP TABLE IF EXISTS requisita CASCADE;
DROP TABLE IF EXISTS oferta CASCADE;

CREATE TABLE departamento(
    codigo int PRIMARY KEY,
    nome text
);

CREATE TABLE professor(
    matricula int PRIMARY KEY,
    dpt int REFERENCES departamento(codigo),
    nome text
);

CREATE TABLE curso(
    codigo int PRIMARY KEY,
    dept int REFERENCES departamento(codigo),
    nome text,
    totalHoras int
);

CREATE TABLE disciplina(
    codigo int PRIMARY KEY,
    dpt int REFERENCES departamento(codigo),
    nome text,
    qtdHoras int
);

CREATE TABLE turma(
    codigo int PRIMARY KEY,
    prof int REFERENCES professor(matricula),
    disc int REFERENCES disciplina(codigo),
    semestre text,
    horaInicio time,
    horaFim time
);

CREATE TABLE aluno(
    matricula int PRIMARY KEY,
    curso int REFERENCES curso(codigo),
    nome text
);

--ALUNO cursa TURMA
CREATE TABLE cursa(
    aluno int REFERENCES aluno(matricula),
    turma int REFERENCES turma(codigo),
    nota float,
    PRIMARY KEY (aluno, turma)
);

--DISCIPLINA requisita DISCIPLINA
CREATE TABLE requisita(
    disciplina1 int REFERENCES disciplina(codigo),
    disciplina2 int REFERENCES disciplina(codigo),
    PRIMARY KEY (disciplina1, disciplina2)
);

--CURSO oferta DISCIPLINA
CREATE TABLE oferta(
    curso int REFERENCES curso(codigo),
    disciplina int REFERENCES disciplina(codigo)
);


--Um professor não pode ministrar disciplinas de outro departamento
CREATE OR REPLACE FUNCTION turma_professor() RETURNS trigger AS $prof_dept$
    DECLARE
        cod_dept_prof int;
        cod_dept_disc int;
    BEGIN
        IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
            SELECT departamento.codigo INTO cod_dept_prof FROM departamento
            INNER JOIN professor ON professor.dpt = departamento.codigo
            WHERE professor.matricula = NEW.prof;
                            
            SELECT departamento.codigo INTO cod_dept_disc FROM departamento
            INNER JOIN disciplina ON disciplina.dpt = departamento.codigo
            WHERE disciplina.codigo = NEW.disc;
                            
            if (cod_dept_prof != cod_dept_disc) THEN
                RAISE EXCEPTION 'Departamento do prof: % | Departamento da disciplina da turma: %', cod_dept_prof, cod_dept_disc;
            END IF;
        END IF;
        RETURN NEW;
    END;
$prof_dept$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_turma_prof ON turma;
CREATE TRIGGER trig_turma_prof
    BEFORE INSERT OR UPDATE ON turma
    FOR EACH ROW EXECUTE PROCEDURE turma_professor();



--Um aluno não pode se inscrever em uma matéria que tenha pré-requisito sem já ter cursado o pré-requisito.
CREATE OR REPLACE FUNCTION tem_requisito() RETURNS trigger AS $mat_req$
    DECLARE
        codigo int;
        nota_mat float;
        cod int;
    BEGIN
        SELECT disc INTO cod FROM turma WHERE NEW.turma = turma.codigo;
        SELECT disciplina2 INTO codigo FROM requisita WHERE disciplina1 = cod;
        IF (codigo IS NOT NULL) THEN
            SELECT nota INTO nota_mat FROM cursa
            WHERE EXISTS (SELECT aluno FROM cursa WHERE codigo=cursa.turma AND cursa.aluno = NEW.aluno);

            IF (nota_mat < 6 OR nota_mat IS NULL) THEN
                RAISE EXCEPTION 'ALUNO NÃO CURSOU O PRÉ REQUISITO %', codigo;
            END IF;
        END IF;
        RETURN NEW;
    END;
$mat_req$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_tem_req ON cursa;
CREATE TRIGGER trig_tem_req
    BEFORE INSERT OR UPDATE ON cursa
    FOR EACH ROW EXECUTE PROCEDURE tem_requisito();


--Um aluno não pode pegar duas turmas da mesma disciplina no mesmo semestre ou uma disciplina em que já foi aprovado
CREATE OR REPLACE FUNCTION cursa_uma_turma() RETURNS trigger AS $$
    DECLARE
        disc_atual int;
        semestre_atual text;
        curs_turma CURSOR (disciplina int) FOR SELECT * FROM cursa INNER JOIN turma ON cursa.turma = turma.codigo WHERE aluno = NEW.aluno AND disc = disciplina;
    BEGIN
        IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
            SELECT turma.disc, turma.semestre INTO disc_atual, semestre_atual FROM turma WHERE turma.codigo = NEW.turma;

            FOR linha IN curs_turma(disc_atual) LOOP
                IF(linha.semestre = semestre_atual) THEN
                    RAISE EXCEPTION 'ALUNO JÁ INSCRITO NA DISCPLINA %', disc_atual;
                ELSIF(linha.nota >= 6) THEN
                    RAISE EXCEPTION 'ALUNO JÁ PASSOU NA DISCIPLINA %. Nota %', disc_atual, linha.nota;
                END IF;
            END LOOP;
        END IF;
        RETURN NEW;
    END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trig_cursa_uma_turma ON cursa;
CREATE TRIGGER trig_cursa_uma_turma
    BEFORE INSERT OR UPDATE ON cursa
    FOR EACH ROW EXECUTE PROCEDURE cursa_uma_turma();


--Cálculo do CR de um aluno
CREATE OR REPLACE FUNCTION calculoCR(codAluno INTEGER) RETURNS REAL AS $$
    DECLARE
        somatorio REAL := 0;    
        tamanho REAL := 0;
        linha RECORD;
    BEGIN
        FOR linha IN
            SELECT 
                cursa.nota AS nota, 
                disciplina.qtdHoras AS horas
            FROM cursa
            INNER JOIN turma ON cursa.turma = turma.codigo
            INNER JOIN disciplina ON turma.disc = disciplina.codigo
            WHERE cursa.aluno = codAluno
        LOOP
            somatorio := somatorio + (linha.nota * linha.horas);
            tamanho := tamanho + linha.horas;
        END LOOP;
        RETURN (somatorio/tamanho);
    END;
$$ LANGUAGE plpgsql;


--Integralização do currículo de um aluno
CREATE OR REPLACE FUNCTION integralizacaoCurriculo(codAluno INTEGER) RETURNS REAL AS $$
    DECLARE
        total_horas real := 0;
        horas_curso real;
        horas_cursadas
            CURSOR FOR SELECT disciplina.qtdHoras AS horas
                FROM cursa
                INNER JOIN turma ON
                    cursa.turma = turma.codigo
                INNER JOIN disciplina ON
                    turma.disc = disciplina.codigo
                WHERE cursa.aluno = codAluno AND cursa.nota >= 6;
    BEGIN
        SELECT curso.totalHoras AS horas INTO STRICT horas_curso
        FROM aluno
        INNER JOIN curso ON
            aluno.curso = curso.codigo
        WHERE aluno.matricula = codAluno;

        FOR linha IN horas_cursadas LOOP
            total_horas := total_horas + linha.horas;
        END LOOP;
        
        RETURN ((100*total_horas)/horas_curso);
    END;
$$ LANGUAGE plpgsql;
