-- Este serve apenas para mudanças na tabela possui skill
CREATE OR REPLACE FUNCTION check_skill() RETURNS trigger AS $check_skill$
	DECLARE
		character_class integer;
	BEGIN
		-- Armazena id da classe do controlável numa variável.
		character_class := (
			SELECT id_classe
			FROM public."Controlavel" AS c
			WHERE c.id = NEW.id
		);
		-- Remove skills do personagem que não fazem parte da sua classe.
		DELETE FROM public."Possui_Skill"
		WHERE id_controlavel = NEW.id
			AND id_skill NOT IN (
				SELECT id_skill
				FROM public."Skill_Pertence_A_Classe"
				WHERE id_classe = NEW.id_classe
			);
		RETURN NEW;
	END;
$check_skill$ LANGUAGE plpgsql;



-- Este serve apenas para mudanças na tabela possui skill
CREATE OR REPLACE FUNCTION check_skill_valid() RETURNS trigger AS $check_skill_valid$
	DECLARE
		character_class integer;
	BEGIN
		-- Armazena id da classe do controlável numa variável.
		character_class := (
			SELECT id_classe
			FROM public."Controlavel" AS c
			WHERE c.id = NEW.id_controlavel
		);
		-- Remove skills do personagem que não fazem parte da sua classe.
		DELETE FROM public."Possui_Skill"
		WHERE id_controlavel = NEW.id_controlavel
			AND id_skill NOT IN (
				SELECT id_skill
				FROM public."Skill_Pertence_A_Classe"
				WHERE id_classe = character_class
			);
		RETURN NEW;
	END;
$check_skill_valid$ LANGUAGE plpgsql;
		
		
		
-- Este serve apenas para mudanças na tabela de skills pertence à classe.
CREATE OR REPLACE FUNCTION check_skill_still_valid() RETURNS TRIGGER AS $check_skill_still_valid$
	DECLARE
		linha record;
	BEGIN
		IF TG_OP = 'DELETE' THEN
			linha = OLD;
		ELSE
			linha = NEW;
		END IF;
		DELETE FROM public."Possui_Skill"
		WHERE id_skill = linha.id_skill
		AND id_controlavel IN (
			SELECT id
			FROM (
				SELECT id, id_classe
				FROM public."Possui_Skill" AS s
				INNER JOIN public."Controlavel" AS c
				ON s.id_controlavel = c.id
				WHERE s.id_skill = linha.id_skill
				
			) AS temp
			WHERE temp.id_classe NOT IN (
				SELECT id_classe
				FROM public."Skill_Pertence_A_Classe"
				WHERE id_skill = linha.id_skill
			)
		);
		RETURN NEW;
	END;
$check_skill_still_valid$ LANGUAGE plpgsql;
	

CREATE TRIGGER check_skill_valid_class AFTER UPDATE ON public."Controlavel"
	FOR EACH ROW EXECUTE PROCEDURE check_skill();

CREATE TRIGGER check_skill_valid_skill AFTER INSERT OR UPDATE ON public."Possui_Skill"
	FOR EACH ROW EXECUTE PROCEDURE check_skill_valid();

CREATE TRIGGER check_skill_still_valid AFTER UPDATE OR DELETE ON public."Skill_Pertence_A_Classe"
	FOR EACH ROW EXECUTE PROCEDURE check_skill_still_valid();