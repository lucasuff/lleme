-- Function: public.valida_cadastro()

-- DROP FUNCTION public.valida_cadastro();

CREATE OR REPLACE FUNCTION public.valida_cadastro()
  RETURNS trigger AS
$BODY$DECLARE 
    busca record;
    tipoFaculdadeInserido integer;
	
BEGIN 
	--Busca cadastro de estudante na base de dados
    SELECT COUNT(*), t."idTipo" INTO busca FROM public."Estudante" as e 
	INNER JOIN public."CursoInstituicao" as ci ON e."idCurso" = ci."idCurso"
	INNER JOIN public."Instituicao" as i ON ci."idInstituicao" = i."idInstituicao"
        INNER JOIN public."Tipo" as t ON i."idTipo" = t."idTipo"
	WHERE e.cpf = NEW."cpf" 
	GROUP BY e."cpf", t."idTipo";

	--Verifica tipo da instituição do novo curso cadastrado
    SELECT t."idTipo" INTO tipoFaculdadeInserido FROM public."Estudante" as e 
	INNER JOIN public."CursoInstituicao" as ci ON e."idCurso" = ci."idCurso"
	INNER JOIN public."Instituicao" as i ON ci."idInstituicao" = i."idInstituicao"
        INNER JOIN public."Tipo" as t ON i."idTipo" = t."idTipo"
	WHERE e."idCurso" = NEW."idCurso";
	
	--1: particular / 2: publica
	--Se o registro que eu quero inserir ja tem como
	--cadastro uma faculdade publica
	IF (busca."idTipo" = 2 ) AND (tipoFaculdadeInserido = 2) THEN
		RAISE EXCEPTION '% -- % Estudante não pode estar cadastrado em mais de uma Insitituição de Ensino Superior Pública.',busca, tipoFaculdadeInserido;
	END IF;
    RETURN NEW;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.valida_cadastro()
  OWNER TO postgres;
