CREATE OR REPLACE FUNCTION check_skill_qty() RETURNS trigger AS $check_skill_qty$
	DECLARE
		qty integer;
		aux integer;
		classe_skill integer;
		classe_controlavel integer;
	BEGIN

		-- Verifica se a skill pertence à classe do controlável
		SELECT id_classe INTO classe_skill
			FROM public."Skill_Pertence_A_Classe"
			WHERE id_skill = NEW.id_skill;
		SELECT id_classe INTO classe_controlavel
			FROM public."Controlavel"
			WHERE id = NEW.id_controlavel;
		IF classe_skill != classe_controlavel THEN
			RAISE EXCEPTION 'Skill % não pertence à classe % do controlador', NEW.id_skill, NEW.id_controlavel;
		END IF;

		-- Check se o controlador possui a skill que quer equipar
		SELECT id_skill INTO aux
			FROM public."Possui_Skill"
			WHERE id_controlavel = NEW.id_controlavel
				AND id_skill = NEW.id_skill;
		IF aux IS NULL THEN
			RAISE EXCEPTION 'Controlador % não possui a skill %', NEW.id_controlavel, NEW.id_skill;
		END IF;
		
		--  Verifica se o número máximo de skills equipadas foi alcançada
		IF TG_OP = 'INSERT' THEN
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
		END IF;
	END;
$check_skill_qty$ LANGUAGE plpgsql;

CREATE TRIGGER check_skill_qty BEFORE INSERT OR UPDATE ON public."Equipou_Skill"
	FOR EACH ROW EXECUTE PROCEDURE check_skill_qty();