CREATE OR REPLACE FUNCTION check_skill_qty() RETURNS trigger AS $check_skill_qty$
	DECLARE
		qty integer;
	BEGIN
		qty := (
			SELECT COUNT(id_skill)
			FROM public."Equipou_Skill"
			WHERE id_controlavel = NEW.id_controlavel
		);
		IF qty >= 5 THEN
			RAISE EXCEPTION 'Número máximo de skils equipada. Desequipe uma skill para poder equipar outra.';
		ELSE
			RETURN NEW;
		END IF;
	END;
$check_skill_qty$ LANGUAGE plpgsql;

CREATE TRIGGER check_skill_qty BEFORE INSERT OR UPDATE ON public."Equipou_Skill"
	FOR EACH ROW EXECUTE PROCEDURE check_skill_qty();