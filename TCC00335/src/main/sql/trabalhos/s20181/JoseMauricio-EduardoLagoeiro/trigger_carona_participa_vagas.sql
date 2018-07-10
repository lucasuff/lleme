CREATE OR REPLACE FUNCTION check_vagas() RETURNS trigger AS $check_vagas$
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

        --REGRA 2: Só pode entrar na carona se ainda estiver vagas, ou seja, o número de pessoas participando da carona
        -- deve ser menor ou igual à quantidade de vagas ofertadas para a mesma carona
        SELECT vagas_qtd INTO qtd FROM carona where carona_id = NEW.carona_id; -- Pegado a qtd de vagas para essa carona
        SELECT count(*) INTO qtd_usuarios_carona FROM carona_participa where carona_id = NEW.carona_id; -- Contador de todas as ocorrencias registradas

        IF qtd_usuarios_carona = qtd THEN
            RAISE EXCEPTION 'Carona já está cheia.';
        END IF;

        RETURN NEW;
    END;
$check_vagas$ LANGUAGE plpgsql;

CREATE TRIGGER check_vagas BEFORE INSERT OR UPDATE ON carona_participa
    FOR EACH ROW EXECUTE PROCEDURE check_vagas();