-- Function: public.valida_upload()

-- DROP FUNCTION public.valida_upload();

CREATE OR REPLACE FUNCTION public.valida_upload()
  RETURNS trigger AS
$BODY$DECLARE 
    cursoProva INTEGER;
    cursoEstudante INTEGER;
BEGIN 
    SELECT "idCurso" INTO cursoProva 
	FROM public."MateriaCurso" 
	WHERE "idMateria" = NEW."idMateria";
	
    SELECT c."idCurso" INTO cursoEstudante
	FROM public."Estudante" AS e
	INNER JOIN public."Curso" AS c
	ON e."idCurso" = c."idCurso"
	WHERE e."idEstudante" = NEW."idEstudante";

	IF cursoProva != cursoEstudante THEN
		RAISE EXCEPTION 'Erro ao inserir: Só é possível	fazer upload de provas do mesmo	curso.'; 
	END IF;

    RETURN NEW;
END; $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.valida_upload()
  OWNER TO postgres;
