drop table if exists campeonato cascade;
CREATE TABLE campeonato (
  codigo text NOT NULL,
  nome TEXT NOT NULL,
  ano integer not null, CONSTRAINT campeonato_pk PRIMARY KEY
  (codigo));

drop table if exists time_ cascade;
CREATE TABLE time_ (
  sigla text NOT NULL,
  nome TEXT NOT NULL, CONSTRAINT time_pk PRIMARY KEY
  (sigla));

drop table if exists jogo cascade;
CREATE TABLE jogo (
  campeonato text not null,
  numero integer NOT NULL,
  time1 text NOT NULL,
  time2 text NOT NULL,
  gols1 integer not null,
  gols2 integer not null,
  data_ date not null, CONSTRAINT jogo_pk PRIMARY KEY
  (campeonato,numero), CONSTRAINT jogo_campeonato_fk FOREIGN KEY
  (campeonato) REFERENCES campeonato (codigo), CONSTRAINT jogo_time_fk1 FOREIGN KEY
  (time1) REFERENCES time_ (sigla), CONSTRAINT jogo_time_fk2 FOREIGN KEY
  (time2) REFERENCES time_ (sigla));


CREATE OR REPLACE FUNCTION pontuacao(p_campeonato text, p_pos1 integer, p_pos2 integer)
      RETURNS table (
      campeonato text,
      time_ text,
      pontos smallint,
      vitorias smallint,
      posicao smallint) AS $$
DECLARE
BEGIN
    RETURN
    QUERY
    WITH
        pontos(c,j,t,p) as (select campeonato,numero,time1,3
                            from jogo where gols1 > gols2 AND campeonato = p_campeonato
                            union select campeonato,numero,time2,3
                            from jogo where gols2 > gols1 AND campeonato = p_campeonato
                            union select campeonato,numero,time1,1
                            from jogo where gols1 = gols2 AND campeonato = p_campeonato
                            union select campeonato,numero,time2,1
                            from jogo where gols1 = gols2 AND campeonato = p_campeonato),
        vitorias(c,t,v) as (select campeonato,time1,1
                            from jogo where gols1 > gols2 AND campeonato = p_campeonato
                            union select campeonato,time2,1
                            from jogo where gols2 > gols1 AND campeonato = p_campeonato),
        pontuacao(c,t,tp) as (select c,t,sum(p) as pontos
                              from pontos group by c,t),
        saldo(c,t,tv) as (select c,t,sum(v) as t
                          from vitorias group by c,t)
    select p.c,t.nome,p.tp,s.tv
    from pontuacao as p
         natural join saldo as s
         inner join time_ as t on t.sigla = p.t
    order by p.tp desc, s.tv desc
    limit p_pos2 - p_pos1 +1 offset p_pos1;

    return;
END;$$ LANGUAGE plpgsql
