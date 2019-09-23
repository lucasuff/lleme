CREATE OR REPLACE FUNCTION multiplica(a real[][], b real[][]) RETURNS real[][] AS $$
DECLARE
    i integer;
    j integer;
    k integer;
    acc real;
    c real[][];
    linha real[];
BEGIN
    c = '{}';
    IF (array_length(a, 2) <>  array_length(b, 1)) THEN
        RAISE EXCEPTION 'ERRO DE DIMENSAO';
    ELSE
        FOR i IN 1 .. array_length(a, 1) LOOP
            linha = '{}';
            FOR j IN 1 .. array_length(b, 2) LOOP
                acc = 0;
                FOR k IN 1 .. array_length(a, 2) LOOP
                    acc = acc + a[i][k] * b[k][j];
                END LOOP;
                linha = array_append(linha, acc);
            END LOOP;
            c = array_cat(c, array[linha]);
        END LOOP;

        RETURN c;
    END IF;
END;
$$ LANGUAGE plpgsql;


--SELECT multiplica('{{1,2,10},{3,4,2}}', '{{3,5},{7,6}}');

CREATE OR REPLACE FUNCTION sub_mat(a real[][], lin integer, col integer) RETURNS real[][] AS $$
    DECLARE
        sub real[][] = '{}';
        aux real[];
        i integer;
        j integer;
    BEGIN
        FOR i IN 1 .. array_length(a, 1) LOOP
            IF (i <> lin) THEN
            aux = '{}';
            FOR j IN 1 .. array_length(a, 2) LOOP
                IF (j <> col) THEN
                aux = array_append(aux, a[i][j]);
                END IF;
            END LOOP;
            sub = array_cat(sub, array[aux]);
            END IF;
        END LOOP;
        RETURN sub;
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION determinante(a real[][]) RETURNS integer AS $$
    DECLARE
        j integer;
        acc integer = 0;
    BEGIN
        IF (array_length(a, 1) = 1) THEN
            RETURN a[1][1];
        END IF;
        FOR j IN 1 .. array_length(a, 2) LOOP
            acc = acc + a[1][j] * (-1^(1 + j)) * determinante(sub_mat(a, 1, j));
        END LOOP;
        RETURN acc;
    END;
$$ LANGUAGE plpgsql;


--SELECT sub_mat('{{3,5},{7,6}}', 1, 1);
--SELECT sub_mat('{{1,2,3},{4,5,6},{7,8,9}}', 1, 2);

--SELECT determinante('{{3,5,13,53},{7,6,42,53},{1,2,34,4},{52,2,5,10}}');