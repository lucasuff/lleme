-- Function: public.valida_quantidade()

-- DROP FUNCTION public.valida_quantidade();

CREATE OR REPLACE FUNCTION public.valida_quantidade()
  RETURNS trigger AS
$BODY$DECLARE 
    qtdProvaUpload INTEGER;
    qtdProvaMateria INTEGER;
BEGIN 
    SELECT "qtdprovas" INTO qtdProvaMateria 
	FROM public."Materia" 
	WHERE "idMateria" = NEW."idMateria";
	
    SELECT COUNT(*) INTO qtdProvaUpload
	FROM public."Prova"
	WHERE "idMateria" = NEW."idMateria";

	IF qtdProvaMateria = qtdProvaUpload THEN
		RAISE EXCEPTION 'Erro ao inserir: número máximo de upload de provas por matéria já antigido.'; 
	END IF;

    RETURN NEW;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.valida_quantidade()
  OWNER TO postgres;
