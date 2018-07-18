CREATE OR REPLACE FUNCTION completa_missao() RETURNS TRIGGER AS $$
	DECLARE
		missao record;
		i record;
	BEGIN
		IF TG_OP = 'UPDATE' THEN
			IF (NEW.status = 'COMPLETADA') AND (OLD.status = 'COMPLETADA')
				AND (NEW.id_controlavel = OLD.id_controlavel) AND (NEW.id_missao = OLD.id_missao) THEN
				-- Se estava completada e continua completada, não há o que fazer
				RETURN NEW;
			END IF;
		END IF;
		
		IF (NEW.status <> 'COMPLETADA') THEN
			-- Se a missão ainda não estiver completada, não há o que fazer
			RETURN NEW;
		END IF;
		
		SELECT * INTO missao FROM public."Missao" WHERE id = NEW.id_missao;

		-- Atualiza dinheiro e experiencia do personagem com o valor prometido pela missao
		UPDATE public."Controlavel"
			SET dinheiro = dinheiro + missao.dinheiro, 
				experiencia = experiencia + missao.experiencia
			WHERE id = NEW.id_controlavel;
		
		-- Dá os itens de recompensa ao controlável
		FOR i IN (SELECT id_item, quantidade FROM public."Recompensa" WHERE id_missao = NEW.id_missao) LOOP
			IF EXISTS (SELECT * FROM public."Possui_Itens" WHERE (id_controlavel = NEW.id_controlavel) AND (id_item = i.id_item)) THEN
				UPDATE public."Possui_Itens"
					SET quantidade = quantidade + i.quantidade
					WHERE (id_controlavel = NEW.id_controlavel) AND (id_item = i.id_item);
			ELSE
				INSERT INTO public."Possui_Itens" VALUES
					(NEW.id_controlavel, i.id_item, i.quantidade);
			END IF;
		END LOOP;
			
		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER completa_missao AFTER INSERT OR UPDATE ON public."Realiza_Missao"
	FOR EACH ROW EXECUTE PROCEDURE completa_missao();

