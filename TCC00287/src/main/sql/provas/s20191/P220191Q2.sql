drop table if exists departamento cascade;
create table departamento(
    codigo integer not null,
    nome varchar not null,
    totalSalarios float not null default(0),
    primary key (codigo)
);


drop table if exists empregado cascade;
create table empregado(
    nome varchar not null,
    salario float not null,
    departamento integer not null,
    primary key (nome),
    foreign key (departamento) references departamento(codigo),
    check (salario > 0)
);



CREATE OR REPLACE FUNCTION sync_depto_func() returns trigger as $$
    BEGIN
        IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
            UPDATE departamento
            SET totalSalarios = totalSalarios + NEW.salario
            WHERE codigo = NEW.departamento;
        END IF;

        IF (TG_OP = 'DELETE' OR TG_OP = 'UPDATE') THEN
            UPDATE departamento
            SET totalSalarios = totalSalarios - OLD.salario
            WHERE codigo = OLD.departamento;
        END IF;
        raise notice 'fgagfgf';
        RETURN Null;
    END;
$$ language plpgsql;

CREATE TRIGGER sync_depto
AFTER INSERT OR UPDATE OF salario, departamento OR DELETE ON empregado
FOR EACH ROW EXECUTE PROCEDURE sync_depto_func();

INSERT INTO departamento VALUES (1,'depto 1');
INSERT INTO empregado VALUES ('N1',10.0,1);
INSERT INTO empregado VALUES ('N2',20.0,1);
UPDATE empregado SET salario = salario * 1.1 WHERE nome = 'N1';

select * from departamento;
select * from empregado;