create table produto(
  id bigint not null,
  nome varchar not null);

create table venda(
  _data timestamp not null,
  produto bigint not null,
  qtd integer not null);
 
-- usar d1 e d2
CREATE OR REPLACE FUNCTION best_sellers(d1 date, d2 date) RETURNS
TABLE(anomes int, lista varchar[]) AS $$
    DECLARE
        curs1 CURSOR FOR WITH S1 AS SELECT extract(year from _data)*100 +
        extract(month from _data) as ano_mes, produto, sum(qtd) as _qtd FROM venda
        GROUP BY extract(year from _data)*100 + extract(month from _data), produto;

        SELECT ano_mes, avg(_qtd) as media from S1
        GROUP BY ano_mes;

        curs2(anoMes int, media float) CURSOR FOR
        SELECT produto.nome as nomeProd
        FROM venda INNER JOIN produto ON id = venda.produto
        WHERE ano_mes = anoMes
        GROUP BY produto HAVING sum(qtd) > 1.6 * media;

        bestProd varchar[] = '{}';

    BEGIN
        FOR linha1 in curs1 LOOP
            bestProd = '{}';
            FOR linha2 in curs2(linha1.ano_mes, linha1.media) LOOP
                bestProd = array_append(bestProd, linha2.nomeProd);
            END LOOP;
        RETURN QUERY SELECT linha1.ano_mes, bestProd;
        END LOOP;
    END;
$$ LANGUAGE plpgsql;