CREATE OR REPLACE FUNCTION estado(acoes varchar[])
RETURNS table(acao varchar, qtd integer, preco float)
AS $$

DECLARE
    curs CURSOR(sigla varchar) for SELECT * FROM operacao WHERE acao = sigla;
    auxiliar operacao%rowtype[] = '{}';
    valor_auxiliar integer = 0;

BEGIN
    FOR EACH r IN acoes LOOP
        FOR linha IN curs(r) LOOP
            IF linha.qtd > 0 THEN
                auxiliar = array_append(auxiliar,linha);
            ELSE
                valor_auxiliar = linha.qtd;
                FOR EACH val IN auxiliar LOOP
                    IF valor_auxiliar >= val.qtd THEN
                        valor_auxiliar = linha.qtd - val.qtd;
                        val.qtd = 0;
                    ELSE
                        val.qtd = linha.qtd - valor_auxiliar;
                    END IF;
                END LOOP;
            END IF;
        END LOOP;
        FOR EACH val IN auxiliar LOOP
            IF val.qtd > 0 THEN
                RETURN QUERY SELECT val.acao,val.qtd,val.preco;
            END IF;
        END LOOP;
        val = '{}';
    END LOOP;
END;

$$ LANGUAGE PLPGSQL;