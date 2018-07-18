
/*
RETORNA A QUANTIDADE DE CONSULTAS POR ESPECILIDADE PARA UM DIA DIVIDIDO POR TURNOS
*/

CREATE OR REPLACE FUNCTION escala_dia(IN dia date)
  RETURNS TABLE(d date, num integer, id integer, t integer) AS
$$
begin
drop table if exists temp1;
create temp table temp1(d date, qtd integer,id integer,t integer);

for turno in 0..3 loop
if (turno = 0) then
insert into temp1 ((select dia,count(id_medico),id_especialidade, 0
from consulta 
where data_marcada = dia and hora_inicio > time'06:00' and hora_fim < time'12:00'
group by id_especialidade) );

elsif (turno = 1) then
insert into temp1 ((select dia,count(id_medico),id_especialidade,1
from consulta  
where data_marcada = dia and hora_inicio > time'12:00' and hora_fim < time'18:00'
group by id_especialidade));

elsif(turno = 2) then
insert into temp1 ((select dia,count(id_medico),id_especialidade,2
from consulta
where data_marcada = dia and hora_inicio > time'18:00' and hora_fim < time'24:00'
 group by id_especialidade ));

elsif(turno = 3) then
insert into temp1 ((select dia,count(id_medico),id_especialidade,3
from consulta  
where data_marcada = dia and hora_inicio > time'00:00' and hora_fim < time'06:00'
group by id_especialidade));

end if; 
end loop;
return query select* from temp1;	
END;$$
  LANGUAGE plpgsql;


  
  
  
  
  
  
  
  
  
/*
RETORNA O TURNOS/DIA COM MAIS CONSULTAS DAS ESPECIALIDADES
*/

CREATE OR REPLACE FUNCTION escala_max()
  RETURNS TABLE(d date, tur integer, esp integer) AS
$$
declare
dias date[];

begin

drop table if exists especialidades_por_turno;
create temp table especialidades_por_turno(dia date, qtd integer,id integer,t integer);

dias := ARRAY(select data_marcada from consulta);

raise notice 'Value: %', dias;

for i in 0..array_length(dias,1) loop
insert into especialidades_por_turno select * from escala_dia(dias[i]);
end loop;
return  query select dia,t,id from especialidades_por_turno where qtd=(
		select MAX(qtd) from especialidades_por_turno group by id);

END;$$
  LANGUAGE plpgsql;


  
  
  
  
  
  

  
/*
CONSULTAR ULTIMA DATA POSSÍVEL PARA AGENDAMENTO POR ID DO PACIENTE
*/

CREATE OR REPLACE FUNCTION consultar_reagendamento(IN pas integer)
  RETURNS TABLE(con integer, max date) AS
$$

begin
return query select id_equipamento, data_Retirada
from equipamento_em_sala
where id_equipamento in (select id_equipamento 
			from tipo_consulta_requer_equipamento 
				inner join consulta 
				on tipo_consulta_requer_equipamento.id_tipoconsulta = consulta.id_tipoconsulta
			where id_paciente = pas);

END;$$
  LANGUAGE plpgsql;

  
  
  
  
  
  
  
  
  
/*
CONSULTAR ULTIMA DATA POSSÍVEL PARA AGENDAMENTO POR NOME DO PACIENTE
*/

CREATE OR REPLACE FUNCTION consultar_reagendamento(IN n character varying)
  RETURNS TABLE(con integer, max date) AS
$$

begin
return query select id_equipamento, data_Retirada
from equipamento_em_sala
where id_equipamento in (select id_equipamento 
			from tipo_consulta_requer_equipamento 
				inner join consulta 
				on tipo_consulta_requer_equipamento.id_tipoconsulta = consulta.id_tipoconsulta
			where id_paciente = (select id_paciente from paciente where nome like n));

END;$$
  LANGUAGE plpgsql;


  
  
  
  
  
  
  
  
  
  
/*
Trigger 1:

Consultas que ocorrem simultaneamente não podem ter o mesmo médico, 
nem o mesmo paciente, nem utilizar a mesma sala.
*/


CREATE FUNCTION multi_consulta_check() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
	begin
if exists (select ID_MEDICO
			from consulta 
			where new.data_marcada = data_marcada and ( new.hora_inicio between hora_inicio and hora_Fim 
			or new.hora_fim  between hora_inicio and hora_fim )and NEW.ID_MEDICO = id_medico )
THEN
				RAISE EXCEPTION 'Erro: Medico não disponivel!';
		end if;
	if exists (select ID_MEDICO
			from consulta 
			where new.data_marcada = data_marcada and ( new.hora_inicio between hora_inicio and hora_Fim 
			or new.hora_fim  between hora_inicio and hora_fim )and NEW.ID_SALA = id_sala )
THEN
				RAISE EXCEPTION 'Erro: Sala não disponivel!';
		end if;
	if exists (select ID_MEDICO
			from consulta 
			where new.data_marcada = data_marcada and ( new.hora_inicio between hora_inicio and hora_Fim 
			or new.hora_fim  between hora_inicio and hora_fim )and NEW.ID_paciente = id_paciente )
THEN
				RAISE EXCEPTION 'Erro: Paciente não disponivel!';
		end if;

	return new;
END;$$;

CREATE TRIGGER multiconsulta BEFORE INSERT OR UPDATE ON consulta 
FOR EACH ROW EXECUTE PROCEDURE multi_consulta_check();










/*
Trigger 2:

Uma consulta de um determinado tipo deve ser criada em uma sala que possua os 
equipamentos necessários para aquela consulta na data em que ela ocorrerá.
*/


CREATE FUNCTION tem_equipamento_check() RETURNS trigger
    LANGUAGE plpgsql
    AS $$begin
	DROP TABLE IF EXISTS temp1;
	DROP TABLE IF EXISTS temp2;
	CREATE TEMP TABLE temp1 AS
	select id_equipamento from tipo_consulta_requer_equipamento
	where id_tipoconsulta = new.id_tipoconsulta;

        CREATE TEMP TABLE temp2 AS
	select id_equipamento from equipamento_em_sala
	where id_sala = new.id_sala and 
	new.data_marcada between data_movimentacao and data_retirada;

	if exists (select * from temp1 except select * from temp2) then RAISE EXCEPTION 'Erro: Equipamento não disponivel!';
	end if;
	return new;
	
END; $$;

CREATE TRIGGER temequipamento BEFORE INSERT OR UPDATE ON consulta 
FOR EACH ROW EXECUTE PROCEDURE tem_equipamento_check();










/*
Trigger 3:

Não deve ser possível mover um equipamento de uma sala se já houver 
uma consulta que necessita deste equipamento agendada na mesma.
*/


CREATE FUNCTION tem_consulta_marcada_sala_check() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

begin
	IF (TG_OP = 'DELETE') THEN
		if exists(
			select * from consulta inner join equipamento_em_sala
			on consulta.id_sala=equipamento_em_sala.id_sala 
			where equipamento_em_sala.id_sala = old.id_sala)
		then
			RAISE EXCEPTION 'Erro: Existem uma ou mais consultas marcadas nesta sala que exige este equipamento';
		end if;
		
	ELSIF (TG_OP = 'UPDATE') 
	THEN
		IF(old.id_sala!=new.id_sala or old.id_equipamento!=new.id_equipamento) 
		THEN	
			if exists(
				select * from consulta inner join equipamento_em_sala
				on consulta.id_sala=equipamento_em_sala.id_sala 
				where equipamento_em_sala.id_sala = old.id_sala)
			then
				RAISE EXCEPTION 'Erro: Existem uma ou mais consultas marcadas nesta sala que exige este equipamento';
			end if;	
		end if;
	end if;
	return old;
		
END; $$;

CREATE TRIGGER temconsultasala BEFORE DELETE or UPDATE ON equipamento_em_sala 
FOR EACH ROW EXECUTE PROCEDURE tem_consulta_marcada_sala_check();












