--------- Procedure 1 --------
CREATE OR REPLACE FUNCTION verificar_vinculo() RETURNS TRIGGER AS $$
	DECLARE
		agendamentos_inicio INTEGER;
		agendamentos_fim INTEGER;
		agendamentos INTEGER;
 	BEGIN
		IF old.periodo_fim IS NULL THEN
			old.periodo_fim := now();
		END IF;

		IF TG_OP = 'UPDATE' THEN
			SELECT COUNT(cpf_funcionario) INTO agendamentos_inicio 
			FROM agendamento
			WHERE data_agendada BETWEEN old.periodo_inicio and new.periodo_inicio
			      AND agendamento.cpf_funcionario = old.cpf_funcionario
			      AND id_negocio = old.id_negocio;
			      
			SELECT COUNT(cpf_funcionario) INTO agendamentos_fim
			FROM agendamento
			WHERE data_agendada BETWEEN new.periodo_fim AND old.periodo_fim
			      AND agendamento.cpf_funcionario = old.cpf_funcionario
			      AND id_negocio = old.id_negocio;
			
			
			IF agendamentos_inicio > 0 THEN
				RAISE EXCEPTION 'Nao e possivel alterar pois existem agendamentos feitos antes desse periodo de inicio';
			END IF;

			IF agendamentos_fim > 0 THEN
                            RAISE EXCEPTION 'Nao e possivel alterar pois existem agendamentos marcados';
			END IF;

			RETURN new;
		END IF;

		IF TG_OP = 'DELETE' THEN
			SELECT COUNT(cpf_funcionario) INTO agendamentos
				FROM agendamento 
					WHERE data_agendada BETWEEN old.periodo_inicio AND old.periodo_fim
					AND agendamento.cpf_funcionario = old.cpf_funcionario;

			IF agendamentos > 0 THEN
				RAISE EXCEPTION 'Nao e possivel deletar pois existem agendamentos';
			END IF;
			RETURN NULL;
		END IF;
	END;
$$ LANGUAGE plpgsql;

	--- Trigger 1 ---
	--- Verificar se funcionario pode alterar data de inicio/fim do periodo de trabalho ---
CREATE TRIGGER verificar_vinculo_tgr BEFORE DELETE OR UPDATE ON vinculo_empregaticio
	FOR EACH ROW EXECUTE PROCEDURE verificar_vinculo();  
