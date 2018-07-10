CREATE OR REPLACE FUNCTION check_interesse() RETURNS trigger AS $check_interesse$
	DECLARE
        usuario_faculdade_id int;
        carona_faculdade_id int;
		carona_usuario_id int;
		usuario_interessado_id int;
        qtd_usuarios_carona int;
        qtd int;
    BEGIN

        -- Pegando a faculdade correspondente ao usuário que está se interessando pela carona
		SELECT faculdade_id INTO usuario_faculdade_id FROM usuario where usuario_id = NEW.usuario_id;
        IF usuario_faculdade_id IS NULL THEN
            RAISE EXCEPTION 'Usuário não pertence à nenhuma faculdade.';
        END IF;

        -- Pegando a faculdade correspondente ao campus carona de interesse
		SELECT faculdade_id INTO carona_faculdade_id FROM campus where campus_id = ( select campus_id from carona where carona_id = NEW.carona_id );

        IF carona_faculdade_id !=  usuario_faculdade_id THEN
            RAISE EXCEPTION 'Só pode se interessar por alguma carona oferecida para faculdade do usuário.';
        END IF;

        -- REGRA 1: Um usuário não pode entrar na carona criada por ele mesmo
		usuario_interessado_id := NEW.usuario_id;
		SELECT usuario_id INTO carona_usuario_id FROM carona where carona_id = NEW.carona_id;
        IF carona_usuario_id = usuario_interessado_id THEN
            RAISE EXCEPTION 'Usuário não pode se interessar na carona criada por ele mesmo.';
        END IF;

        RETURN NEW;
    END;
$check_interesse$ LANGUAGE plpgsql;

CREATE TRIGGER check_interesse BEFORE INSERT OR UPDATE ON carona_interesse
    FOR EACH ROW EXECUTE PROCEDURE check_interesse();