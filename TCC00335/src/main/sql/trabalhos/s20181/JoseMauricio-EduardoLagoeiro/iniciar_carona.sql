CREATE OR REPLACE FUNCTION realizar_carona(id int) RETURNS void AS $realizar_carona$
    DECLARE
        qtd int; -- Recebe a quantidade de vagas da carona
        contador int;
        r carona_interesse%rowtype;
    BEGIN
        contador := 0;
        SELECT vagas_qtd INTO qtd FROM carona WHERE carona_id = id;
        RAISE NOTICE 'Quantidade de vagas da carona: %.', qtd;

        FOR r IN
            SELECT * FROM carona_interesse WHERE carona_id = id ORDER BY criado_em LIMIT qtd
        LOOP
            -- processar aqui
            EXECUTE 'INSERT INTO carona_participa (carona_id,usuario_id) VALUES ($1, $2)'
                USING r.carona_id, r.usuario_id;
            contador := contador + 1;
        END LOOP;
        
        UPDATE carona SET carona_status = 'REALIZADA' WHERE carona_id = id;

        RAISE NOTICE 'Carona realizada com % passageiros.', contador;

    END;
$realizar_carona$ LANGUAGE plpgsql;