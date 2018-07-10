-- REGRA: NÃO PODE TROCAR A FACULDADE DE ALGUM USUÁRIO QUE OFERECEU CARONA PARA OUTRA FACULDADE.

CREATE OR REPLACE FUNCTION check_usuario_faculdade() RETURNS trigger AS $check_usuario_faculdade$
	DECLARE
        num_caronas int;
    BEGIN
        IF NEW.faculdade_id != OLD.faculdade_id THEN
            SELECT count(*) INTO num_caronas FROM caronas WHERE usuario_id = NEW.usuario_id;
            IF num_caronas > 0 THEN
                RAISE EXCEPTION 'Usuário não podo trocar de faculdade com caronas atribuídas à outra.';
            END IF;
        END IF;
        RETURN NEW;
    END;
$check_usuario_faculdade$ LANGUAGE plpgsql;

CREATE TRIGGER check_usuario_faculdade BEFORE UPDATE ON usuario
    FOR EACH ROW EXECUTE PROCEDURE check_usuario_faculdade();