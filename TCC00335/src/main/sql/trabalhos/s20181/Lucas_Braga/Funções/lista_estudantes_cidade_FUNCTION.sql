-- Function: public.lista_estudantes_cidade(character varying)

-- DROP FUNCTION public.lista_estudantes_cidade(character varying);

CREATE OR REPLACE FUNCTION public.lista_estudantes_cidade(p_cidade character varying)
  RETURNS integer AS
$BODY$DECLARE
  quantidade INTEGER;
BEGIN
	SELECT COUNT(*) INTO quantidade FROM public."Estudante" as a
	INNER JOIN public."Endereco" as b ON a."idEndereco" = b."idEndereco"
	WHERE b."cidade" = p_cidade;
	RETURN quantidade;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.lista_estudantes_cidade(character varying)
  OWNER TO postgres;
COMMENT ON FUNCTION public.lista_estudantes_cidade(character varying) IS 'Lista a quantidade de alunos por cidade';

