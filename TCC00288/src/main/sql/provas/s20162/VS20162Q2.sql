CREATE TABLE empregado_audit(
    operacao       char(1) NOT NULL,
    data_hora      timestamp NOT NULL,
    userid         text NOT NULL,
    nome_antigo    TEXT,
    salario_antigo REAL,
    nome_novo      TEXT,
    salario_novo   REAL
);

CREATE OR REPLACE FUNCTION audit_empregado() RETURNS TRIGGER AS $$
    BEGIN
            IF (TG_OP = 'DELETE') THEN
            INSERT INTO empregado_audit SELECT 'D', now(), USER, OLD.*;
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO empregado_audit SELECT 'U', now(), USER, OLD.*, NEW.*;
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO empregado_audit SELECT 'I', now(), USER, NULL, NULL, NEW.*;
            RETURN NEW;
        END IF;
        RETURN NULL;
    END;$$ LANGUAGE plpgsql;

CREATE TRIGGER audit_empregado_tgr BEFORE INSERT OR UPDATE OR DELETE ON empregado
    FOR EACH ROW EXECUTE PROCEDURE audit_empregado();
