-- -----------------------------------------------------
-- Table users
-- -----------------------------------------------------
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users (
  idusers SERIAL,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NULL,
  tel VARCHAR(45) NULL,
  gender VARCHAR(45) NULL,
  PRIMARY KEY (idusers));


-- -----------------------------------------------------
-- Table restaurant
-- -----------------------------------------------------
DROP TABLE IF EXISTS restaurant CASCADE;
CREATE TABLE IF NOT EXISTS restaurant (
  idrestaurant SERIAL,
  restname VARCHAR(45) NOT NULL,
  restgrade INT NULL,
  latitude FLOAT NULL,
  longitude FLOAT NULL,
  is_blocked BOOLEAN NULL,
  PRIMARY KEY (idrestaurant));


-- -----------------------------------------------------
-- Table schedule
-- -----------------------------------------------------
DROP TABLE IF EXISTS schedule CASCADE;
CREATE TABLE IF NOT EXISTS schedule (
  idschedule SERIAL,
  iduser INT NOT NULL,
  scheduled_date DATE NOT NULL,
  idrest INT NOT NULL,
  amountppl INT NOT NULL,
  amounttables INT NOT NULL,
  observations VARCHAR(45) NULL,
  PRIMARY KEY (idschedule),
  CONSTRAINT users
    FOREIGN KEY (iduser)
    REFERENCES users (idusers),
  CONSTRAINT rest
    FOREIGN KEY (idrest)
    REFERENCES restaurant (idrestaurant));


-- -----------------------------------------------------
-- Table food
-- -----------------------------------------------------
DROP TABLE IF EXISTS food CASCADE;
CREATE TABLE IF NOT EXISTS food (
  idfood SERIAL,
  foodname VARCHAR(45) NOT NULL,
  PRIMARY KEY (idfood));


-- -----------------------------------------------------
-- Table restaurant_food
-- -----------------------------------------------------
DROP TABLE IF EXISTS restaurant_food CASCADE;
CREATE TABLE IF NOT EXISTS restaurant_food (
  idrestaurant_food SERIAL,
  idrest INT NOT NULL,
  idfood INT NOT NULL,
  PRIMARY KEY (idrestaurant_food),
  CONSTRAINT rest
    FOREIGN KEY (idrest)
    REFERENCES restaurant (idrestaurant),
  CONSTRAINT food
    FOREIGN KEY (idfood)
    REFERENCES food (idfood));
	
-- -----------------------------------------------------
-- Table food_preferences
-- -----------------------------------------------------
DROP TABLE IF EXISTS food_preferences CASCADE;
CREATE TABLE IF NOT EXISTS food_preferences(
	idfood_preferences SERIAL,
	iduser INT NOT NULL,
	idfood INT NOT NULL,
	PRIMARY KEY (idfood_preferences),
	CONSTRAINT users
		FOREIGN KEY (iduser)
		REFERENCES users (idusers),
	CONSTRAINT food
		FOREIGN KEY (idfood)
		REFERENCES food (idfood));


-- -----------------------------------------------------
-- Table open
-- -----------------------------------------------------
DROP TABLE IF EXISTS opened CASCADE;
CREATE TABLE IF NOT EXISTS opened (
  idopen SERIAL,
  dayopen INT NULL,
  starthour VARCHAR(45) NULL,
  endhour VARCHAR(45) NULL,
  opendate DATE NULL,
  isopen BOOLEAN NULL,
  tablesaval INT NULL,
  PRIMARY KEY (idopen));


-- -----------------------------------------------------
-- Table open_when
-- -----------------------------------------------------
DROP TABLE IF EXISTS open_when CASCADE;
CREATE TABLE IF NOT EXISTS open_when (
  idopen_when SERIAL,
  idrest INT NOT NULL,
  idopen INT NOT NULL,
  PRIMARY KEY (idopen_when),
  CONSTRAINT rest
    FOREIGN KEY (idrest)
    REFERENCES restaurant (idrestaurant),
  CONSTRAINT openconst
    FOREIGN KEY (idopen)
    REFERENCES opened (idopen));

-- -----------------------------------------------------
-- Table address
-- -----------------------------------------------------
DROP TABLE IF EXISTS address CASCADE;
CREATE TABLE IF NOT EXISTS address(
	idaddress SERIAL,
	city VARCHAR(45),
	country VARCHAR(45),
	zipcode VARCHAR(45),
	street VARCHAR(45),
	nInt INT,
	comp VARCHAR(45),
	latitude FLOAT,
	longitude FLOAT,
	PRIMARY KEY (idaddress));

-- -----------------------------------------------------
-- Table address
-- -----------------------------------------------------
DROP TABLE IF EXISTS rest_address CASCADE;
CREATE TABLE IF NOT EXISTS rest_adress(
	idrest_address SERIAL,
	idrest INT NOT NULL,
	idaddress INT NOT NULL,
	PRIMARY KEY (idrest_address),
	CONSTRAINT rest
	    FOREIGN KEY (idrest)
    	REFERENCES restaurant (idrestaurant),
  	CONSTRAINT address
    	FOREIGN KEY (idaddress)
	    REFERENCES address (idaddress));

-- -----------------------------------------------------
-- Table checkin
-- -----------------------------------------------------
DROP TABLE IF EXISTS checkin CASCADE;

CREATE TABLE IF NOT EXISTS checkin (
  idcheckin SERIAL,
  idschedule INT NULL,
  iduser INT NULL,
  datecheckin DATE NULL,
  previous_scheduled BOOLEAN NULL,
  tablesamount INT NULL,
  idrest INT NULL,
  PRIMARY KEY (idcheckin),
  CONSTRAINT schedule
    FOREIGN KEY (idschedule)
    REFERENCES schedule (idschedule),
  CONSTRAINT users
    FOREIGN KEY (iduser)
    REFERENCES users (idusers),
  CONSTRAINT rest
    FOREIGN KEY (idrest)
    REFERENCES restaurant (idrestaurant));


-- -----------------------------------------------------
-- Table rating
-- -----------------------------------------------------
DROP TABLE IF EXISTS rating CASCADE ;

CREATE TABLE IF NOT EXISTS rating (
  idrating SERIAL,
  foodrating FLOAT NULL,
  servicerating FLOAT NULL,
  overralrating FLOAT NULL,
  idcheckin INT NULL,
  daterating DATE NULL,
  observations VARCHAR(45) NULL,
  PRIMARY KEY (idrating),
  CONSTRAINT checkin
    FOREIGN KEY (idcheckin)
    REFERENCES checkin (idcheckin));
	
-----------------------------------------------------------------------------------
--
-- Triggers
--
-----------------------------------------------------------------------------------

--------------------------------------------------------
-- PRIMEIRA REGRA SEMÂNTICA, AS MESAS DE RESERVA E CHECKIN NAO DEVE ULTRAPASSAR AS MESAS DISPONIVEIS
---------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION public.checkin_consistency()
    RETURNS trigger
    LANGUAGE 'plpgsql'
     NOT LEAKPROOF
AS $BODY$BEGIN
	DECLARE
	day_number integer := -1;
	i integer := 0;
	tablesav integer := 0;
	rec record;
	BEGIN
	
	RAISE NOTICE 'BEGINNING TRIGGER';
	RAISE NOTICE 'Current DATE = %', NEW.datecheckin;
	FOR rec in select * from public.schedule WHERE new.idrest = public.schedule.idrest AND new.datecheckin =
		public.schedule.scheduled_date LOOP
		i = i + rec.amounttables;
	END LOOP;
	RAISE NOTICE 'Total de mesas reservadas para a data % = %', now(),i;
	
	SELECT INTO day_number EXTRACT (DOW FROM NEW.datecheckin);
	RAISE NOTICE 'DAY NUMBER = %', day_number;
	
	SELECT tablesaval INTO tablesav FROM opened WHERE idopen = (SELECT idopen FROM open_when WHERE NEW.idrest = open_when.idrest) AND
		dayopen = day_number;
	RAISE NOTICE 'Tables aval for this day = %', tablesav;
	
	IF (NEW.tablesamount + i) > tablesav THEN
		RAISE NOTICE 'Indisp amount of tables';
		RETURN NULL;
	ELSE
		RAISE NOTICE 'OK';
		RETURN NEW;
	END IF;
	
	END;
	
END;$BODY$;

ALTER FUNCTION public.checkin_consistency()
    OWNER TO postgres;

CREATE TRIGGER checkin_consistency
    BEFORE INSERT OR DELETE OR UPDATE 
    ON public.checkin
    FOR EACH ROW
    EXECUTE PROCEDURE public.checkin_consistency();
	
---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------
-- SEGUNDO TRIGGER ( ALTERANDO NUMERO DE MESAS DISPONIVEIS PARA UM DIA, VERIFICAR SE JA NAO HÁ RESERVAS SUPERIOR AO NOVO NUMERO)
---------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION public.change_table()
    RETURNS trigger
    LANGUAGE 'plpgsql'
     NOT LEAKPROOF
AS $BODY$BEGIN
	DECLARE
	day_changed integer := 0;
	tables_scheduled integer := 0;
	rec record;
	rest_id integer := 0;
	
	
	BEGIN
	SELECT idrest INTO rest_id FROM open_when WHERE NEW.idopen = open_when.idopen;
	RAISE NOTICE 'Starting TRIGGER CHANGE_TABLE';
	RAISE NOTICE 'New Tables Amount = %', NEW.tablesaval;
	SELECT INTO day_changed EXTRACT (DOW FROM NEW.opendate);
	RAISE NOTICE 'The day changed is %', day_changed;
	FOR rec in SELECT scheduled_date, sum(amounttables) as amount FROM (
		SELECT * FROM schedule WHERE schedule.idrest = rest_id AND day_changed = (SELECT EXTRACT(DOW FROM schedule.scheduled_date))) as schedule GROUP BY scheduled_date HAVING scheduled_date > now() LOOP		
		
		IF rec.amount > NEW.tablesaval THEN
			RAISE NOTICE 'Cant change tables amount for this day';
			RETURN NULL;
		END IF;
	
	RETURN NEW;
	END LOOP;
	--FOR rec in SELECT amounttables FROM schedule WHERE scheduled_date > now() AND schedule.idrest = idrest LOOP
	--	RAISE NOTICE '%',rec.amounttables;
	--END LOOP;
	
	RETURN NULL;
	END;
	
END;$BODY$;

ALTER FUNCTION public.change_table()
    OWNER TO postgres;

CREATE TRIGGER change_grade
    BEFORE UPDATE
    ON public.opened
    FOR EACH ROW
    EXECUTE PROCEDURE public.change_table();

---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------
-- TERCEIRO TRIGGER ( AO INSERIR OU ATUALIZAR A TABELA DE RESERVAS NAO DEVE ULTRAPASSAR AS MESAS DISPONIVEIS)
---------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION public.schedule_consistency()
    RETURNS trigger
    LANGUAGE 'plpgsql'
     NOT LEAKPROOF
AS $BODY$BEGIN
	DECLARE
	rec record;
	day_number integer := -1;
	tables_scheduled integer := 0;
	tables_aval integer := 0;
	
	BEGIN
	RAISE NOTICE '';
	RAISE NOTICE '==================================';
	RAISE NOTICE 'Schedule Consistency';
	SELECT INTO day_number EXTRACT (DOW FROM NEW.scheduled_date);
	RAISE NOTICE 'DAY NUMBER = %', day_number;
	SELECT tablesaval INTO tables_aval FROM opened WHERE idopen = (SELECT idopen FROM open_when WHERE NEW.idrest = open_when.idrest) AND
		dayopen = day_number;
	RAISE NOTICE 'Tables aval for this day = %', tables_aval;
	FOR rec in select * from schedule WHERE new.idrest = schedule.idrest AND new.scheduled_date =
		schedule.scheduled_date LOOP
		tables_scheduled = tables_scheduled + rec.amounttables;
	END LOOP;
	
	RAISE NOTICE 'Current tables Scheduled = %', tables_scheduled;
	RAISE NOTICE 'Tables Requested = %', tables_scheduled + NEW.amounttables;
	
	IF tables_aval < (tables_scheduled + NEW.amounttables) THEN
		RAISE NOTICE 'Not Enough Tables Available';
		RETURN NULL;
	ELSE
		RAISE NOTICE 'Schedule Done';
		RETURN NEW;
	END IF;
	RAISE NOTICE '==================================';
	RAISE NOTICE '';
	
	END;
END;$BODY$;

ALTER FUNCTION public.schedule_consistency()
    OWNER TO postgres;

CREATE TRIGGER schedule_consistency
    BEFORE INSERT OR UPDATE
    ON public.schedule
    FOR EACH ROW
    EXECUTE PROCEDURE public.schedule_consistency();

---------------------------------------------------------------------------------------
-- SEGUNDA REGRA SEMÂNTICA, SEM RESERVAS OU CHECKINS EM DIAS FECHADOS
---------------------------------------------------------------------------------------
--------------------------------------------------------
-- PRIMEIRO TRIGGER
--------------------------------------------------------
CREATE OR REPLACE FUNCTION public.close_day_schedule()
    RETURNS trigger
    LANGUAGE 'plpgsql'
     NOT LEAKPROOF
AS $BODY$BEGIN
	DECLARE
	is_open boolean;
	day_number integer := -1;
	BEGIN
	
	RAISE NOTICE '';
	RAISE NOTICE '==================================';
	RAISE NOTICE 'Close Day Schedule';
	SELECT INTO day_number EXTRACT (DOW FROM NEW.scheduled_date);
	SELECT isopen INTO is_open FROM  open_when INNER JOIN opened ON open_when.idopen = opened.idopen WHERE open_when.idrest = NEW.idrest AND opened.dayopen = day_number;
	IF is_open THEN
		RAISE NOTICE 'Rest is open, Schedule ok';
		RETURN NEW;
	ELSE
		RAISE NOTICE 'Rest is not open';
		RETURN NULL;
	END IF;
	RAISE NOTICE '';
	RAISE NOTICE '==================================';
	END;
	
END;$BODY$;

ALTER FUNCTION public.close_day_schedule()
    OWNER TO postgres;

CREATE TRIGGER close_day_schedule
    BEFORE UPDATE OR INSERT
    ON public.schedule
    FOR EACH ROW
    EXECUTE PROCEDURE public.close_day_schedule();

--------------------------------------------------------
-- SEGUNDO TRIGGER
--------------------------------------------------------
CREATE OR REPLACE FUNCTION public.close_day_checkin()
    RETURNS trigger
    LANGUAGE 'plpgsql'
     NOT LEAKPROOF
AS $BODY$BEGIN
	DECLARE
	is_open boolean;
	day_number integer := -1;
	BEGIN
	
	RAISE NOTICE '';
	RAISE NOTICE '==================================';
	RAISE NOTICE 'Close Day Schedule';
	SELECT INTO day_number EXTRACT (DOW FROM NEW.datecheckin);
	SELECT isopen INTO is_open FROM  open_when INNER JOIN opened ON open_when.idopen = opened.idopen WHERE open_when.idrest = NEW.idrest AND opened.dayopen = day_number;
	IF is_open THEN
		RAISE NOTICE 'Rest is open, Schedule ok';
		RETURN NEW;
	ELSE
		RAISE NOTICE 'Rest is not open';
		RETURN NULL;
	END IF;
	RAISE NOTICE '';
	RAISE NOTICE '==================================';
	END;
	
END;$BODY$;

ALTER FUNCTION public.close_day_checkin()
    OWNER TO postgres;

CREATE TRIGGER close_day_checkin
    BEFORE UPDATE OR INSERT
    ON public.checkin
    FOR EACH ROW
    EXECUTE PROCEDURE public.close_day_checkin();
---------------------------------------------------------------------------------------
-- TERCEIRA REGRA SEMÂNTICA, PARA BLOQUEAR UM RESTAURANTE DEVE-SE FECHAR TODOS OS DIAS ANTES
-- E NAO PODE HAVER RESTAURANTES COM NOTA MENOR QUE 5 NAO BLOQUEADOS
---------------------------------------------------------------------------------------
--------------------------------------------------------
-- PRIMEIRO TRIGGER
--------------------------------------------------------
CREATE OR REPLACE FUNCTION public.block_rest_schedule()
    RETURNS trigger
    LANGUAGE 'plpgsql'
     NOT LEAKPROOF
AS $BODY$BEGIN
	DECLARE
	is_open boolean;
	day_number integer := -1;
	is_block boolean;
	BEGIN
	
	RAISE NOTICE '';
	RAISE NOTICE '==================================';
	RAISE NOTICE 'Block Rest Schedule';
	SELECT is_blocked INTO is_block FROM restaurant WHERE NEW.idrest = idrestaurant;
	IF is_block THEN
		RETURN NULL;
	ELSE
		RETURN NEW;
	END IF;
	RAISE NOTICE '';
	RAISE NOTICE '==================================';
	END;
	
END;$BODY$;

ALTER FUNCTION public.block_rest_schedule()
    OWNER TO postgres;

CREATE TRIGGER block_rest_schedule
    BEFORE UPDATE OR INSERT
    ON public.schedule
    FOR EACH ROW
    EXECUTE PROCEDURE public.block_rest_schedule();

--------------------------------------------------------
-- SEGUNDO TRIGGER
--------------------------------------------------------
CREATE OR REPLACE FUNCTION public.block_rest_close_days()
    RETURNS trigger
    LANGUAGE 'plpgsql'
     NOT LEAKPROOF
AS $BODY$BEGIN
	DECLARE
	rec record;
	BEGIN
	
	RAISE NOTICE '';
	RAISE NOTICE '==================================';
	RAISE NOTICE 'Block Rest Close Days';
	IF OLD.is_blocked = FALSE THEN
		IF NEW.isblocked THEN
			FOR rec IN SELECT * FROM opened INNER JOIN open_when ON idrest = idrest WHERE open_when.idrest = NEW.idrestaurant LOOP
				IF rec.is_open THEN
					RAISE NOTICE 'Restaurante nao pode estar bloqueado com dias abertos';
					RETURN NULL;
				END IF;
			END LOOP;
		END IF;
	END IF;
	RAISE NOTICE 'Restaurant Blocked';
	RETURN NEW;
	RAISE NOTICE '';
	RAISE NOTICE '==================================';
	END;
	
END;$BODY$;

ALTER FUNCTION public.block_rest_close_days()
    OWNER TO postgres;

CREATE TRIGGER block_rest_close_days
    BEFORE UPDATE OR INSERT
    ON public.restaurant
    FOR EACH ROW
    EXECUTE PROCEDURE public.block_rest_close_days();



---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------
-- PRIMEIRA FUNÇÃO
---------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION get_users(rest integer) RETURNS TABLE(id integer, name text) AS $$
DECLARE
rec record;
BEGIN
   DROP TABLE IF EXISTS response CASCADE ;
   CREATE TABLE response(
   id integer,
   name text);
   FOR rec in SELECT * FROM users INNER JOIN checkin ON idusers = iduser WHERE idrest = rest LOOP
      INSERT INTO response(id, name) VALUES (rec.idusers, rec.first_name);
   END LOOP;
   RETURN QUERY SELECT * from response;
END;

$$ LANGUAGE plpgsql;

---------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------
-- SEGUNDA FUNÇÃO
---------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION get_users_rated(rest integer, grade float) RETURNS TABLE(id integer, name text) AS $$
DECLARE
rec record;
rec2 record;
BEGIN
   DROP TABLE IF EXISTS response2 CASCADE ;
   CREATE TABLE response2(
   id integer,
   name text);
   FOR rec IN SELECT * FROM users INNER JOIN checkin ON idusers = iduser WHERE idrest = rest LOOP
      FOR rec2 in SELECT * FROM rating WHERE rating.idcheckin = rec.idcheckin LOOP
	  	IF rec2.foodrating > grade THEN
			INSERT INTO response2(id, name) VALUES (rec.idusers, rec.first_name);
		END IF;
	  END LOOP;
   END LOOP;
   RETURN QUERY SELECT * from response2;
END;	

$$ LANGUAGE plpgsql;

--------------------------------------------------------------------------------------------------
-- POPULATE DATABASE
-- INSERT INTO FOOD TABLE
-------------------------------------------------
INSERT INTO food(foodname) VALUES('comida japonesa');
INSERT INTO food(foodname) VALUES('fastfood');

-- INSERT INTO USERS TABLE
-------------------------------------------------
INSERT INTO users(first_name, last_name, tel, gender) VALUES('John', 'Doe', '21-99999-9999','M');

-- INSERT INTO RESTAURANT
-------------------------------------------------
INSERT INTO restaurant(restname, restgrade) VALUES('L"ETOILE', 9.8);

-- INSERT FOOD INTO RESTAURANTS
-------------------------------------------------
INSERT INTO restaurant_food(idrest, idfood) VALUES(1, 1);

-- INSERT INTO OPEN DAYS TABLE AMOUNT
-------------------------------------------------
INSERT INTO opened(dayopen, opendate, isopen, tablesaval) VALUES(3, '	2019-12-25', TRUE, 30);
INSERT INTO open_when(idrest, idopen) VALUES(1,1);

-- INSERT INTO SCHEDULE
-------------------------------------------------
INSERT INTO schedule(iduser, scheduled_date, idrest, amountppl, amounttables) VALUES(1, '2019-12-25', 1, 5, 2);
INSERT INTO schedule(iduser, scheduled_date, idrest, amountppl, amounttables) VALUES(1, '2019-12-25', 1, 10, 4);
INSERT INTO schedule(iduser, scheduled_date, idrest, amountppl, amounttables) VALUES(1, '2019-12-25', 1, 2, 1);
INSERT INTO schedule(iduser, scheduled_date, idrest, amountppl, amounttables) VALUES(1, '2019-12-25', 1, 4, 1);
INSERT INTO schedule(iduser, scheduled_date, idrest, amountppl, amounttables) VALUES(1, '2019-12-25', 1, 8, 2);

-- INSERT INTO CHECKIN
-------------------------------------------------
INSERT INTO checkin(iduser, datecheckin, previous_scheduled, tablesamount, idrest) VALUES(1, '2019-12-25', FALSE, 2, 1);
INSERT INTO checkin(iduser, datecheckin, previous_scheduled, tablesamount, idrest) VALUES(1, '2019-12-25', FALSE, 21, 1);

--------------------------------------------------------------------------------------------------
INSERT INTO rating(foodrating, servicerating, idcheckin, daterating, observations) 
	VALUES(9.6, 9.5, 1, '2019-12-26', 'TAMO JUNTO');
--------------------------------------------------------------------------------------------------

UPDATE opened SET tablesaval = 10 WHERE idopen = 1;

--------------------------------------------------------------------------------------------------
-- FUNÇÕES
--------------------------------------------------------------------------------------------------
SELECT get_users(1);
SELECT get_users_rated(1, 5.5);
