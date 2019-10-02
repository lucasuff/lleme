drop table if exists operacao cascade;
create table operacao(
    acao varchar,
    "data" timestamp,
    qtd integer,
    preco float,
    primary key (acao,"data"),
    check (qtd != 0)
);

insert into operacao values('A1','2019-05-16'::timestamp,100,10.5);
insert into operacao values('A1','2019-05-18'::timestamp,-85,10.5);
insert into operacao values('A1','2019-05-19'::timestamp,100,10.5);
insert into operacao values('A2','2019-05-16'::timestamp,200,8.5);
insert into operacao values('A2','2019-05-17'::timestamp,50,9.);
insert into operacao values('A2','2019-05-18'::timestamp,150,19.);
insert into operacao values('A2','2019-05-20'::timestamp,-100,9.5);
insert into operacao values('A2','2019-05-21'::timestamp,-125,19.5);
insert into operacao values('A3','2019-05-16'::timestamp,300,7.5);

DROP FUNCTION estado(acoes varchar[]);
CREATE OR REPLACE FUNCTION estado(acoes varchar[])
RETURNS table(acao varchar, qtd integer, preco float) AS $$
DECLARE
    operacoes CURSOR(sigla varchar) for
        SELECT * FROM operacao WHERE operacao.acao = sigla
        order by "data";
    carteira float[][];
    val int = 0;
    venda integer = 0;
    acao varchar;
BEGIN
    FOREACH acao IN ARRAY acoes LOOP
        carteira = '{}';
        FOR oper IN operacoes(acao) LOOP
            IF oper.qtd > 0 THEN
                carteira = carteira || array[[oper.qtd::float,oper.preco]];
            ELSE
                venda = oper.qtd;
                FOR lote in 1..array_length(carteira, 1) LOOP
                    IF carteira[lote][1] > 0 THEN
                        IF abs(venda) > carteira[lote][1] then
                            carteira[lote][1] = 0;
                            venda = venda + carteira[lote][1];
                        ELSE
                            carteira[lote][1] = carteira[lote][1] + venda;
                            venda = 0;
                            exit;
                        END IF;
                    END IF;
                END LOOP;
            END IF;
        END LOOP;
        FOR lote IN 1..array_length(carteira, 1) LOOP
            IF carteira[lote][1] > 0 THEN
                RETURN QUERY SELECT acao, carteira[lote][1]::INTEGER, carteira[lote][2];
            END IF;
        END LOOP;
    END LOOP;
END;
$$ LANGUAGE PLPGSQL;

--SELECT * from operacao;

SELECT estado(ARRAY['A1', 'A2', 'A3']);