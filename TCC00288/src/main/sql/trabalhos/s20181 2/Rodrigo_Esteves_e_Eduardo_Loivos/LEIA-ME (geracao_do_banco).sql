/*****************************************************************************
NESTE ARQUIVO ESTÁ O CÓDIGO SQL PARA GERAR E POPULAR AS TABELAS DA BASE, 
ASSIM COMO SUAS PROCEDURES E TRIGGERS
O ARQUIVO DEVE SER RODADO PARA ALGUMA DATABASE EXISTENTE
EXISTE TAMBÉM UM ARQUIVO CHAMADO triggers_procedures SEPARADO COM AS FUNÇÕES E TRIGGERS, 
APENAS PARA FACILITAR NA HORA DE ENCONTRA-LOS 
******************************************************************************/



DROP TABLE IF EXISTS equipamento cascade;

CREATE TABLE equipamento
(
  id_equipamento integer NOT NULL,
  descricao text,
  nome character varying(50) NOT NULL,
  CONSTRAINT "PKEY_EQUIPAMENTO" PRIMARY KEY (id_equipamento)
);


DROP TABLE IF EXISTS especialidade cascade;

CREATE TABLE especialidade
(
  id_especialidade integer NOT NULL,
  nome character varying(20) NOT NULL,
  CONSTRAINT "PKEY_ESPECIALIDADE" PRIMARY KEY (id_especialidade)
);

DROP TABLE IF EXISTS medico cascade;

CREATE TABLE medico
(
  id_medico integer NOT NULL,
  matricula character varying(10) NOT NULL,
  crm character varying(12) NOT NULL,
  nome character varying(50) NOT NULL,
  CONSTRAINT "PKEY_MEDICO" PRIMARY KEY (id_medico),
  CONSTRAINT unq_crm UNIQUE (crm),
  CONSTRAINT unq_mat UNIQUE (matricula)
);


DROP TABLE IF EXISTS paciente cascade;

CREATE TABLE paciente
(
  id_paciente integer NOT NULL,
  telefone character varying(15),
  email character varying(50),
  cpf character varying(11) NOT NULL,
  nome character varying(50) NOT NULL,
  CONSTRAINT "PKEY_PACIENTE" PRIMARY KEY (id_paciente)
);


DROP TABLE IF EXISTS sala cascade;

CREATE TABLE sala
(
  id_sala integer NOT NULL,
  localizacao text,
  CONSTRAINT "PKEY_SALA" PRIMARY KEY (id_sala)
);



DROP TABLE IF EXISTS tipo_consulta cascade;

CREATE TABLE tipo_consulta
(
  id_tipoconsulta integer NOT NULL,
  nome_tipoconsulta character varying(40) NOT NULL,
  descricao text,
  CONSTRAINT "PKEY_TIPOCONSULTA" PRIMARY KEY (id_tipoconsulta)
);




DROP TABLE IF EXISTS equipamento_em_sala cascade;

CREATE TABLE equipamento_em_sala
(
  id_sala integer NOT NULL,
  id_equipamento integer NOT NULL,
  data_movimentacao date NOT NULL,
  data_retirada date NOT NULL,
  CONSTRAINT "PKEY_EQUIPAMENTOEMSALA" PRIMARY KEY (id_sala, id_equipamento, data_movimentacao),
  CONSTRAINT "FKEY_IDEQUIPAMENTO" FOREIGN KEY (id_equipamento)
      REFERENCES equipamento (id_equipamento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FKEY_IDSAL" FOREIGN KEY (id_sala)
      REFERENCES sala (id_sala) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);




DROP TABLE IF EXISTS medico_tem_especialidade cascade;

CREATE TABLE medico_tem_especialidade
(
  id_medico integer NOT NULL,
  id_especialidade integer NOT NULL,
  CONSTRAINT "PKEY_MEDICOESPECIALIDADE" PRIMARY KEY (id_medico, id_especialidade),
  CONSTRAINT "FKEY_IDESPECIALIDADE" FOREIGN KEY (id_especialidade)
      REFERENCES especialidade (id_especialidade) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FKEY_IDMEDICO" FOREIGN KEY (id_medico)
      REFERENCES medico (id_medico) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);





DROP TABLE IF EXISTS tipo_consulta_requer_equipamento cascade;

CREATE TABLE tipo_consulta_requer_equipamento
(
  id_tipoconsulta integer NOT NULL,
  id_equipamento integer NOT NULL,
  CONSTRAINT "PKEY_TIPOCONSULTAEQUIP" PRIMARY KEY (id_tipoconsulta, id_equipamento),
  CONSTRAINT "FKEY_IDEQUIPAMENTO" FOREIGN KEY (id_equipamento)
      REFERENCES equipamento (id_equipamento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FKEY_IDTIPOCONSULTA" FOREIGN KEY (id_tipoconsulta)
      REFERENCES tipo_consulta (id_tipoconsulta) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


DROP TABLE IF EXISTS consulta cascade;
CREATE TABLE consulta
(
  id_medico integer NOT NULL,
  id_sala integer NOT NULL,
  id_paciente integer NOT NULL,
  id_especialidade integer NOT NULL,
  id_tipoconsulta integer NOT NULL,
  data_marcada date NOT NULL,
  hora_inicio time without time zone NOT NULL,
  hora_fim time without time zone NOT NULL,
  CONSTRAINT "PKEY_CONSULTA" PRIMARY KEY (id_medico, id_sala, id_paciente, data_marcada, hora_inicio),
  CONSTRAINT "FKEY_IDESPECIALIDADE" FOREIGN KEY (id_especialidade)
      REFERENCES especialidade (id_especialidade) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FKEY_IDMEDICO" FOREIGN KEY (id_medico)
      REFERENCES medico (id_medico) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FKEY_IDPACIENTE" FOREIGN KEY (id_paciente)
      REFERENCES paciente (id_paciente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FKEY_IDSALA" FOREIGN KEY (id_sala)
      REFERENCES sala (id_sala) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "FKEY_IDTIPOCONSULTA" FOREIGN KEY (id_tipoconsulta)
      REFERENCES tipo_consulta (id_tipoconsulta) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO paciente VALUES
(1,'21980884407','','13454345587','Jose dos Santos'),
(2,'21980885507','','00345345697','Eduardo da Silva Oliveira'),
(3,'21980886608','','12095496523','Cleber Medina Albuquerque'),
(4,'21990284457','','34593245609','Maria dos Santos Teixeira'),
(5,'21982885507','','98623419454','Ana Claudia Guimarães'),
(6,'21982232407','','12345954352','Joana de Souza Filho'),
(7,'21980884407','','65434582345','Leticia Mendes de Sá'),
(8,'21980889902','lari.gouveia@gmail.com','65124382345','Larissa Gouveia dos Anjos');

INSERT INTO sala VALUES
(1,'Sala 412, localizada no quarto andar'),
(2,'Sala 403, localizada no quarto andar'),
(3,'Sala 530, localizada no quinto andar'),
(4,'Sala 630, localizada no sexto andar'),
(5,'Sala 219, localizada no segundo andar'),
(6,'Sala 427, localizada no quarto andar');

INSERT INTO equipamento VALUES
(1,'','Laringoscopio'),
(2,'','Oftalmoscopio'),
(3,'','Esfigmomanômetro'),
(4,'','Otoscópio'),
(5,'','Oxímetro'),
(6,'Doppler Vascular de Mesa - Medpej - DF-7000-VB c/ Bateria','Doppler Vascular'),
(7,'','Estetoscópio pediatrico'),
(8,'','Estetoscópio cardiológico'),
(9,'Detector Fetal Digital de Mesa - Medpej - DF-7000-DB c/ Bateria','Detector fetal'),
(10,'','Eletrocardiógrafo'),
(11,'','Endoscópio'),
(12,'','Videocolonoscópio');

INSERT INTO tipo_consulta VALUES
(1,'Consulta - Clínica geral',''),
(2,'Exame - Eletrocardiograma',''),
(3,'Exame - Ecocardiograma',''),
(4,'Exame - Endoscopia',''),
(5,'Consulta - Gastroenterologia',''),
(6,'Consulta - Cardiologia',''),
(7,'Consulta - Alergia',''),
(8,'Exame - Colonoscopia',''),
(9,'Exame - Radiografia',''),
(10,'Consulta - Ortopedia','');

INSERT INTO medico VALUES
(1,'64699','RJ-23490','João Nogueira Mendes'),
(2,'646','RJ-15490','Cleber dos Santos'),
(3,'622','RJ-17470','Miguel da Silva Oliveira'),
(4,'822','RJ-10394','Marina Medina Albuquerque'),
(5,'900','RJ-6348','Julia dos Santos Teixeira'),
(6,'1024','RJ-21234','João Claudio Guimarães'),
(7,'699','RJ-33950','Alex de Souza Filho'),
(8,'647','RJ-1754','Yan Mendes de Sá');


INSERT INTO especialidade VALUES
(1,'Cardiologia'),
(2,'Neurologia'),
(3,'Psiquiatria'),
(4,'Alergia'),
(5,'Otorrinolaringologia'),
(6,'Oncologia'),
(7,'Ortopedia'),
(8,'Pediatria'),
(9,'Urologia'),
(10,'Endocrinologia'),
(11,'Gastroenterologia');


INSERT INTO tipo_consulta_requer_equipamento VALUES
(1,8),
(1,3),
(2,10),
(4,11),
(5,3),
(6,3),
(5,8),
(6,8),
(8,12);

INSERT INTO medico_tem_especialidade VALUES
(1,1),
(2,1),
(3,2),
(4,3),
(5,5),
(6,4),
(7,7),
(8,11);

INSERT INTO equipamento_em_sala VALUES
(1,10,'2018-06-10','2018-06-20');


INSERT INTO consulta VALUES
(1,1,1,1,2,'2018-06-12','14:30:00','15:00:00'),
(1,1,2,1,2,'2018-06-12','15:30:00','16:00:00'),
(1,1,1,1,2,'2018-06-12','00:30:00','01:00:00');




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


CREATE TRIGGER multiconsulta BEFORE INSERT OR UPDATE ON consulta 
FOR EACH ROW EXECUTE PROCEDURE multi_consulta_check();


CREATE TRIGGER temconsultasala BEFORE DELETE ON equipamento_em_sala 
FOR EACH ROW EXECUTE PROCEDURE tem_consulta_marcada_sala_check();


CREATE TRIGGER temequipamento BEFORE INSERT OR UPDATE ON consulta 
FOR EACH ROW EXECUTE PROCEDURE tem_equipamento_check();

