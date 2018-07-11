CREATE OR REPLACE FUNCTION calcular_qtd_servico(negocio INTEGER, data_inicio timestamp, data_fim timestamp) RETURNS 
	TABLE(cpf TEXT, nome_funcionario TEXT, quantidade_servicos bigint)  AS $$
	BEGIN
		RETURN QUERY WITH 
			T1 AS (SELECT cpf_funcionario FROM vinculo_empregaticio ve
				WHERE ((ve.periodo_inicio BETWEEN data_inicio AND data_fim) 
					AND (ve.periodo_fim BETWEEN data_inicio AND data_fim)) 
					AND id_negocio = negocio),
			T2 AS (SELECT * FROM agendamento WHERE hora_fim IS NOT NULL 
				AND id_negocio = negocio 
				AND (data_agendada BETWEEN data_inicio AND data_fim)
				AND cpf_funcionario IN (SELECT * FROM T1)),
			T3 AS (SELECT cpf_funcionario, COUNT(*) AS quantidade_servicos FROM T2 GROUP BY cpf_funcionario)
			
			SELECT f.CPF, f.nome,T3.quantidade_servicos FROM T3,Funcionario f 
				WHERE f.cpf = T3.cpf_funcionario; 
			RETURN;
	END;
$$ LANGUAGE plpgsql;

--- Usada para retornar quantidade de servicos realizados pelos funcionarios associados a um negócio em um tempo determinado --
SELECT * FROM calcular_qtd_servico(1, timestamp '01-01-16', timestamp '01-01-19');

CREATE OR REPLACE FUNCTION estatisticas_funcionario(cpf_f TEXT, data_inicio timestamp, data_fim timestamp) RETURNS 
TABLE(id INTEGER, nome TEXT,QNT_ESTABELECIMENTOS_VINCULADOS BIGINT,
	  CANCELADOS bigint, PENDENTES bigint, CONCLUIDOS bigint,
	  DURACAO_MEDIA TEXT,TEMPO_TRABALHADO TEXT ) AS $$
	DECLARE
		val RECORD;
	BEGIN
		FOR val in SELECT ts.id, ts.nome_servico
						FROM servico_funcionario sf, tipo_servico ts
						WHERE sf.cpf_funcionario = cpf_f
							AND sf.tipo_servico = ts.ID 
		LOOP
			id := val.id;
			nome := val.nome_servico;
			
			SELECT count(ag.id_negocio) 
			INTO QNT_ESTABELECIMENTOS_VINCULADOS 
			FROM agendamento ag 
			WHERE ag.cpf_funcionario = cpf_f
				AND ag.tipo_servico = val.id
				AND ag.data_agendada BETWEEN data_inicio AND data_fim
				AND ag.status = 1
				AND ag.hora_fim IS NOT NULL;
			
			SELECT TO_CHAR(COALESCE(AVG((EXTRACT(EPOCH FROM ag.hora_fim - ag.hora_inicio)/60)), 0), '9990')||' min',
				   TO_CHAR(COALESCE(SUM((EXTRACT(EPOCH FROM ag.hora_fim - ag.hora_inicio)/60)), 0), '9990')||' min'
			INTO DURACAO_MEDIA,TEMPO_TRABALHADO
			FROM agendamento ag
			WHERE ag.cpf_funcionario = cpf_f
				AND ag.tipo_servico = val.id
				AND ag.data_agendada BETWEEN data_inicio AND data_fim
				AND ag.status = 1
				AND ag.hora_fim IS NOT NULL;

			
			SELECT count(*) INTO CONCLUIDOS 
			FROM agendamento ag 
			WHERE ag.cpf_funcionario = cpf_f
				AND ag.tipo_servico = val.id
				AND ag.data_agendada BETWEEN data_inicio AND data_fim
				AND ag.status = 1
				AND ag.hora_fim IS NOT NULL;
				
			SELECT count(*) INTO PENDENTES 
			FROM agendamento ag 
			WHERE ag.cpf_funcionario = cpf_f
				AND ag.tipo_servico = val.id
				AND ag.data_agendada BETWEEN data_inicio AND data_fim
				AND ag.status = 1
				AND ag.hora_fim IS NULL;
					
			SELECT count(*) INTO CANCELADOS 
			FROM agendamento ag 
			WHERE ag.cpf_funcionario = cpf_f
				AND ag.tipo_servico = val.id
				AND ag.data_agendada BETWEEN data_inicio AND data_fim
				AND ag.status = 0;
			RETURN NEXT;	
		END LOOP;
		END;
$$ LANGUAGE plpgsql;

--- Usada para retornar um relatorio de agendamentos sobre o funcionario informado ---
select * from estatisticas_funcionario('3111111112', timestamp '01-01-16', timestamp '01-01-29');


