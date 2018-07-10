
-- REGRA: NÃO PODE TROCAR O DONO DO CARRO QUE ESTÁ ATRIBUÍDO À ALGUMA CARONA DE OUTRO USUARIO

CREATE OR REPLACE FUNCTION check_carro_usuario() RETURNS trigger AS $check_carro_usuario$
	DECLARE
        num_caronas int;
    BEGIN
        IF NEW.usuario_id != OLD.usuario_id THEN
            SELECT count(*) INTO num_caronas FROM caronas WHERE carro_id = NEW.carro_id;
            IF num_caronas > 0 THEN
                RAISE EXCEPTION 'Há caronas atribuídas à esse carro com outro usuário. Não pode ser trocado';
            END IF;
        END IF;
        RETURN NEW;
    END;
$check_carro_usuario$ LANGUAGE plpgsql;

CREATE TRIGGER check_carro_usuario BEFORE UPDATE ON carro
    FOR EACH ROW EXECUTE PROCEDURE check_carro_usuario();