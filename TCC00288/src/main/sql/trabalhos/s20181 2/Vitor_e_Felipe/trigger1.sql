CREATE OR REPLACE FUNCTION check_initial_attributes() RETURNS trigger AS $check_initial_attributes$
	BEGIN
		IF NEW.nivel = 1 THEN

			IF (NEW.forca > 5 or NEW.agilidade > 5 or NEW.vitalidade > 5 or NEW.destreza > 5) THEN
				RAISE EXCEPTION 'Personagem de jogador recem criado nao pode ter mais de 5 em quaisquer atributos.';
				RETURN NULL;
			END IF;
			
			IF (NEW.forca < 0 or NEW.agilidade < 0 or NEW.vitalidade < 0 or NEW.destreza < 0) THEN
				RAISE EXCEPTION 'Personagem de jogador nao pode ter menos de 0 em quaisquer atributos.';
				RETURN NULL;
			END IF;
			
			RETURN NEW;
		END IF;
		RETURN NEW;
	END;
$check_initial_attributes$ LANGUAGE plpgsql;

CREATE TRIGGER check_initial_attributes BEFORE INSERT ON public."Controlavel"
		FOR EACH ROW EXECUTE PROCEDURE check_initial_attributes();