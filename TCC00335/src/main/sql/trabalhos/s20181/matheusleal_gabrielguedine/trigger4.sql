--- Procedure 4 ---
CREATE OR REPLACE FUNCTION funcionario_nao_faz_mais_o_servico() RETURNS TRIGGER AS $$
	DECLARE
		qnt_servicos_pendentes INTEGER;
	BEGIN
			SELECT COUNT(*) INTO qnt_servicos_pendentes 
				FROM agendamento
 				WHERE data_agendada > localtimestamp
				AND cpf_funcionario = old.cpf_funcionario 
 				AND tipo_servico = old.tipo_servico
				AND status = 1;
				
			IF qnt_servicos_pendentes > 0 THEN
				RAISE EXCEPTION 'Funcionario ainda possui agendamentos a serem feitos com o servico anterior.'
			END IF;			
			IF TG_OP = 'UPDATE' THEN
				RETURN new;
			END IF;
			IF TG_OP = 'DELETE' THEN
				RETURN NULL;
			END IF;
	END;
$$ LANGUAGE plpgsql;

--- Trigger 4 ---
--- Verifica se ao alterar ou excluir um servico do funcionario ele possui agendamentos a realizar ---
CREATE TRIGGER funcionario_nao_faz_mais_o_servico_trg BEFORE UPDATE OR DELETE
ON servico_funcionario FOR EACH ROW EXECUTE PROCEDURE funcionario_nao_faz_mais_o_servico();


