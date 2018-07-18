CREATE OR REPLACE FUNCTION level_up() RETURNS TRIGGER AS $$
	DECLARE
		new_level integer;
	BEGIN
		IF NEW.experiencia <> OLD.experiencia THEN
			new_level = 1 + floor(0.1 * |/NEW.experiencia);
			IF new_level > NEW.nivel THEN
				NEW.nivel = new_level;
			END IF;
		END IF;
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER level_up BEFORE UPDATE ON public."Controlavel"
	FOR EACH ROW EXECUTE PROCEDURE level_up();