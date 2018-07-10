-- Function: public.valida_alt_qtd_provas()

-- DROP FUNCTION public.valida_alt_qtd_provas();

CREATE OR REPLACE FUNCTION public.valida_alt_qtd_provas()
  RETURNS trigger AS
$BODY$DECLARE 
    qtdProvaUpload INTEGER;
BEGIN 
	
    SELECT COUNT(*) INTO qtdProvaUpload
	FROM public."Prova"
	WHERE "idMateria" = NEW."idMateria";

	IF NEW."qtdprovas" < qtdProvaUpload THEN
		RAISE EXCEPTION 'Erro ao atualizar quantidade de provas da materia por semestre.'; 
	END IF;

    RETURN NEW;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.valida_alt_qtd_provas()
  OWNER TO postgres;
