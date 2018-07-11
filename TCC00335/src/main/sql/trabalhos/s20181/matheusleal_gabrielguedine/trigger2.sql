	--- Procedure 2 ---
CREATE OR REPLACE FUNCTION garantir_funcionario_alocado() RETURNS TRIGGER AS $$
	DECLARE
		qtd_negocio_vinculo INTEGER;
	BEGIN
		SELECT COUNT(cpf_funcionario) INTO qtd_negocio_vinculo 
			FROM vinculo_empregaticio AS ve
				WHERE NEW.cpf_funcionario = ve.cpf_funcionario AND NEW.id_negocio = ve.id_negocio
					AND NEW.data_agendada BETWEEN ve.periodo_inicio AND ve.periodo_fim;

		IF qtd_negocio_vinculo < 1 THEN
			RAISE EXCEPTION 'Funcionario nao esta cadastrado nesse negocio.';
		END IF;

		RETURN NEW;
	END;
$$ LANGUAGE plpgsql;

	--- Trigger 2 ----
	--- Verificar se funcionario ao cadastrar agendamento, está cadastrado ao negocio designado --
CREATE TRIGGER garantir_funcionario_alocado_trg BEFORE INSERT OR UPDATE  ON agendamento
	FOR EACH ROW EXECUTE PROCEDURE garantir_funcionario_alocado(); 

