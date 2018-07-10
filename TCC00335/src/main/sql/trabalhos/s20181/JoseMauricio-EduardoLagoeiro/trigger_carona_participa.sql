CREATE OR REPLACE FUNCTION atualiza_avaliacao() RETURNS trigger AS $atualiza_avaliacao$
	DECLARE
        carona_usuario_id int;
        contador int;
    BEGIN
        SELECT usuario_id INTO carona_usuario_id FROM carona WHERE carona_id = NEW.carona_id;
        SELECT COUNT(*) INTO contador FROM usuario_avaliacao WHERE usuario_id = carona_usuario_id; -- Se já tiver avaliação retorna 1 senão 0

        IF TG_OP = 'INSERT' THEN
            IF NEW.avaliacao IS NOT NULL THEN
                IF contador = 1 THEN -- Se já tiver avaliação
                    UPDATE usuario_avaliacao 
                        SET qtd_avaliacao = qtd_avaliacao + 1, 
                            avaliacao_media = ( (avaliacao_media * qtd_avaliacao + NEW.avaliacao) / (qtd_avaliacao + 1) )
                        WHERE usuario_id = carona_usuario_id;
                ELSE -- Senão
                    INSERT INTO usuario_avaliacao (usuario_id, avaliacao_media, qtd_avaliacao) VALUES (carona_usuario_id, NEW.avaliacao, 1);
                END IF;
            END IF;
        END IF;

        IF TG_OP = 'UPDATE' THEN
            -- Fazer a diff
            IF OLD.avaliacao IS NOT NULL and NEW.avaliacao IS NOT NULL THEN 
                IF contador = 1 THEN -- Se já tiver avaliação
                    UPDATE usuario_avaliacao 
                        SET qtd_avaliacao = qtd_avaliacao + 1, 
                            avaliacao_media = ( (avaliacao_media/qtd_avaliacao) + (OLD.avaliacao - NEW.avaliacao)/qtd_avaliacao )
                        WHERE usuario_id = carona_usuario_id;
                ELSE -- Senão
                    INSERT INTO usuario_avaliacao (usuario_id, avaliacao_media, qtd_avaliacao) VALUES (carona_usuario_id, NEW.avaliacao, 1);
                END IF;
            -- Comportamento do insert
            ELSIF OLD.avaliacao IS NULL and NEW.avaliacao IS NOT NULL THEN 
                IF contador = 1 THEN -- Se já tiver avaliação
                    UPDATE usuario_avaliacao 
                        SET qtd_avaliacao = qtd_avaliacao + 1, 
                            avaliacao_media = ( (avaliacao_media * qtd_avaliacao + NEW.avaliacao) / (qtd_avaliacao + 1) )
                        WHERE usuario_id = carona_usuario_id;
                ELSE -- Senão
                    INSERT INTO usuario_avaliacao (usuario_id, avaliacao_media, qtd_avaliacao) VALUES (carona_usuario_id, NEW.avaliacao, 1);
                END IF;
            -- Comportamento do delete
            ELSIF OLD.avaliacao IS NOT NULL and NEW.avaliacao IS NULL THEN 
                IF contador = 1 THEN
                    UPDATE usuario_avaliacao 
                        SET qtd_avaliacao = qtd_avaliacao - 1, 
                            avaliacao_media = ( (avaliacao_media * qtd_avaliacao - OLD.avaliacao) / (qtd_avaliacao - 1) )
                        WHERE usuario_id = carona_usuario_id;
                END IF;
            END IF;
        END IF;

        IF TG_OP = 'DELETE' THEN
            IF OLD.avaliacao IS NOT NULL THEN
                IF contador = 1 THEN
                    UPDATE usuario_avaliacao 
                        SET qtd_avaliacao = qtd_avaliacao - 1, 
                            avaliacao_media = ( (avaliacao_media * qtd_avaliacao - OLD.avaliacao) / (qtd_avaliacao - 1) )
                        WHERE usuario_id = carona_usuario_id;
                END IF;
            END IF;
        END IF;
    
        RETURN NEW;
    END;
$atualiza_avaliacao$ LANGUAGE plpgsql;

CREATE TRIGGER atualiza_avaliacao BEFORE INSERT OR UPDATE OR DELETE ON carona_participa
    FOR EACH ROW EXECUTE PROCEDURE atualiza_avaliacao();