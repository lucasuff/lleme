-- Este serve apenas para mudanças na tabela possui skill
CREATE OR REPLACE FUNCTION check_skill() RETURNS trigger AS $check_skill$
	DECLARE
		character_class integer;
		aux integer;
	BEGIN
		-- Verificar se o controlavel tem uma skill equipada que não condiz com a sua classe
		SELECT id_skill INTO aux FROM public."Equipou_Skill"
			WHERE id_controlavel = NEW.id
				AND id_skill NOT IN (
						SELECT id_skill
						FROM public."Skill_Pertence_A_Classe"
						WHERE id_classe = NEW.id_classe
					);
		IF aux IS NOT NULL THEN
			RAISE EXCEPTION 'Controlável tem skills equipadas que não condizem com sua nova classe. Remova as skills papra manter a integridade od banco de dados';
			RETURN NULL;
		END IF;
		-- Verificar se o controlavel possui skill que não condiz com a sua classe
		SELECT id_skill INTO aux FROM public."Possui_Skill"
			WHERE id_controlavel = NEW.id
				AND id_skill NOT IN (
						SELECT id_skill
						FROM public."Skill_Pertence_A_Classe"
						WHERE id_classe = NEW.id_classe
					);
		IF aux IS NOT NULL THEN
			RAISE EXCEPTION 'Controlável possui skills que não condizem com sua nova classe. Remova as skills papra manter a integridade od banco de dados';
			RETURN NULL;
		END IF;
		-- Caso tudo esteja OK, permite a mudança
		RETURN NEW;
	END;
$check_skill$ LANGUAGE plpgsql;



-- Este serve apenas para mudanças na tabela possui skill
CREATE OR REPLACE FUNCTION check_skill_valid() RETURNS trigger AS $check_skill_valid$
	DECLARE
		character_class integer;
		aux integer;
	BEGIN
		-- Armazena id da classe do controlável numa variável.
		character_class := (
			SELECT id_classe
			FROM public."Controlavel" AS c
			WHERE c.id = NEW.id_controlavel
		);
		-- Armazena id da classe da skill
		SELECT id_classe INTO aux
			FROM public."Skill_Pertence_A_Classe"
			WHERE id_skill = NEW.id_skill
				AND id_classe = character_class;
		-- Verificar se a skill não condiz com a classe do controlável
		IF aux IS NULL THEN
			RAISE EXCEPTION 'A skill % não pertence à classe % do personagem', NEW.id_skill, character_class;
			RETURN NULL;
		END IF;
		-- Caso a skill seja compatível, permite a mudança
		RETURN NEW;
	END;
$check_skill_valid$ LANGUAGE plpgsql;
		
		
		
-- Este serve apenas para mudanças na tabela de skills pertence à classe.
CREATE OR REPLACE FUNCTION check_skill_still_valid() RETURNS TRIGGER AS $check_skill_still_valid$
	DECLARE
		linha record;
		aux integer;
	BEGIN
		IF TG_OP = 'DELETE' THEN
			-- PERAE a gente precisa se preocupar com isso?
			-- É só especificar ON DELETE CASCADE na tabela Skill_Pertence_A_Classe, não?
			-- Verificar se há controláveis que possuem a skill
			SELECT id_controlavel INTO aux
				FROM public."Possui_Skill"
				WHERE id_skill = OLD.id_skill;
			IF aux IS NOT NULL THEN
				RAISE EXCEPTION 'Não é possível excluir a skill, pois há personagens que possuem a skill';
				RETURN NULL;
			END IF;
			-- Verificar se há controláveis com a skill equipada
			SELECT id_controlavel INTO aux
				FROM public."Equipou_Skill"
				WHERE id_skill = OLD.id_skill;
			IF aux IS NOT NULL THEN
				RAISE EXCEPTION 'Não é possível excluir a skill, pois há personagens com a skill equipada';
				RETURN NULL;
			END IF;
			-- Caso tudo esteja OK, permite a mudança
			RETURN NEW;
		ELSE
			-- Verifica se há alguém que possui a skill, com classe diferente que a da skill
			SELECT id INTO aux
				FROM (
					SELECT id, id_classe
					FROM public."Possui_Skill" AS s
					INNER JOIN public."Controlavel" AS c
					ON s.id_controlavel = c.id
					WHERE s.id_skill = linha.id_skill
				) AS temp
				WHERE temp.id_classe != NEW.id_classe;
			IF aux IS NOT NULL THEN
				RAISE EXCEPTION 'Integridade violada: há personagem que possui a skill, mas tem classe diferente da classe nova.';
				RETURN NULL;
			END IF;
			-- Verifica se há alguém com a skill equipada, com classe diferente que a da skill
			SELECT id INTO aux
				FROM (
					SELECT id, id_classe
					FROM public."Equipou_Skill" AS e
					INNER JOIN public."Controlavel" AS c
					ON e.id_controlavel = c.id
					WHERE e.id_skill = linha.id_skill
				) AS temp
				WHERE temp.id_classe != NEW.id_classe;
			IF aux IS NOT NULL THEN
				RAISE EXCEPTION 'Integridade violada: há personagem com a skill equipada, mas pertence a uma classe diferente da classe nova.';
				RETURN NULL;
			END IF;
			-- Caso esteja tudo OK, permite a mudança
			RETURN NEW;
		END IF;
	END;
$check_skill_still_valid$ LANGUAGE plpgsql;
	

CREATE TRIGGER check_skill_valid_class AFTER UPDATE ON public."Controlavel"
	FOR EACH ROW EXECUTE PROCEDURE check_skill();

CREATE TRIGGER check_skill_valid_skill AFTER INSERT OR UPDATE ON public."Possui_Skill"
	FOR EACH ROW EXECUTE PROCEDURE check_skill_valid();

CREATE TRIGGER check_skill_valid_equipou AFTER INSERT OR UPDATE ON public."Equipou_Skill"
	FOR EACH ROW EXECUTE PROCEDURE check_skill_valid();

CREATE TRIGGER check_skill_still_valid AFTER UPDATE OR DELETE ON public."Skill_Pertence_A_Classe"
	FOR EACH ROW EXECUTE PROCEDURE check_skill_still_valid();