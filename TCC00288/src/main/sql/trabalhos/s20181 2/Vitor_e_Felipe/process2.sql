CREATE OR REPLACE FUNCTION class_up() RETURNS trigger AS $$
DECLARE
	current_class record;
BEGIN
	IF NEW.nivel <> OLD.nivel THEN
		-- pegar o nível da classe a que se deve evoluir
		SELECT proxima_classe, nivel_proxima_classe INTO current_class
			FROM public."Classe"
			WHERE id = NEW.id_classe;
		-- comparar se o controlavel atingiu esse nivel
		IF current_class.proxima_classe IS NOT NULL THEN
			IF NEW.nivel >= current_class.nivel_proxima_classe THEN
				UPDATE public."Controlavel"
					SET id_classe = current_class.proxima_classe
					WHERE id = NEW.id;
				-- caso sim, mudar o nível e desequipar todas as skills que a classe não possui
				DELETE FROM public."Possui_Skill" AS ps
					WHERE ps.id_controlavel = NEW.id
					AND ps.id_skill NOT IN (
						SELECT id_skill
						FROM public."Skill_Pertence_A_Classe"
						WHERE id_classe = current_class.proxima_classe
					);
			END IF;
		END IF;
		-- caso não, acaba o programa
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER class_up AFTER UPDATE ON public."Controlavel"
	FOR EACH ROW EXECUTE PROCEDURE class_up();