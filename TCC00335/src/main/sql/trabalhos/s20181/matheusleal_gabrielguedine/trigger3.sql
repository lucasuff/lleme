	--- Procedure 3 ---
CREATE OR REPLACE FUNCTION verificar_funcionario_presta_servico_escolhido() RETURNS TRIGGER AS $$
	DECLARE
		contagem_servico INTEGER;
	BEGIN
		SELECT COUNT(*) INTO contagem_servico
		FROM servico_funcionario
		WHERE tipo_servico = new.tipo_servico AND cpf_funcionario = new.cpf_funcionario;

		IF contagem_servico >0 THEN
			RETURN new;
		ELSE
			RAISE EXCEPTION 'Funcionario nao presta servico escolhido.';
		END IF;
	END;
$$ LANGUAGE plpgsql;
 
	--- Trigger 3 ---
	--- Verifica se funcionario presta servico associado ao agendamento ---
CREATE TRIGGER verificar_funcionario_presta_servico_escolhido_trg
BEFORE INSERT OR UPDATE ON agendamento FOR EACH ROW EXECUTE PROCEDURE
verificar_funcionario_presta_servico_escolhido();


