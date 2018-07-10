-- Function: public."getCpf"()

-- DROP FUNCTION public."getCpf"();

CREATE OR REPLACE FUNCTION public."getCpf"()
  RETURNS TABLE(count bigint, cpf character varying) AS
$BODY$BEGIN
	RETURN QUERY SELECT COUNT(*), e."cpf" FROM public."Estudante" as e GROUP BY e."cpf" HAVING COUNT(*)>1;
	RETURN;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public."getCpf"()
  OWNER TO postgres;
COMMENT ON FUNCTION public."getCpf"() IS 'Busca o CPF dos alunos cadastrados em mais de uma instituiçao de ensino';
