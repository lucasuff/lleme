CREATE OR REPLACE FUNCTION check_carona() RETURNS trigger AS $check_carona$
	DECLARE
		carona_faculdade_id int;
		usuario_faculdade_id int;

        carro_usuario_id int;
    BEGIN
    
        -- O campus sinalizado na carona deve pertencer à faculdade do usuário criador da carona
        -- Pegando a faculdade correspondente ao campus da nova carona 
		SELECT faculdade_id INTO carona_faculdade_id FROM campus where campus_id = NEW.campus_id;
        -- Pegando a faculdade correspondente ao usuário que está criando a carona
		SELECT faculdade_id INTO usuario_faculdade_id FROM usuario where usuario_id = NEW.usuario_id;

        -- Verificando se ele não tem faculdade
        IF usuario_faculdade_id IS NULL THEN
            RAISE EXCEPTION 'Usuário não pertence à nenhuma faculdade.';
        END IF;

        IF carona_faculdade_id !=  usuario_faculdade_id THEN
            RAISE EXCEPTION 'A carona deve ser oferecida para alguma campus da faculdade do motorista.';
        END IF;

        -- REGRA 3: O carro da carona deve pertencer ao usuário criador da carona
        -- Pegando o usuário dono do carro da carona criada
        SELECT usuario_id INTO carro_usuario_id FROM carro where carro_id = NEW.carro_id;
        IF carro_usuario_id != NEW.usuario_id THEN
            RAISE EXCEPTION 'O carro deve pertencer ao criador da carona.';
        END IF;

        RETURN NEW;
    END;
$check_carona$ LANGUAGE plpgsql;

CREATE TRIGGER check_carona BEFORE INSERT OR UPDATE ON carona
    FOR EACH ROW EXECUTE PROCEDURE check_carona();