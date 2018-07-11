DROP TABLE IF EXISTS hotel cascade;
CREATE TABLE hotel (
  numero integer NOT NULL primary key,
  nome TEXT NOT NULL);

DROP TABLE IF EXISTS reserva cascade;
CREATE TABLE reserva (
  numero integer NOT NULL primary key,
  hotel integer NOT NULL REFERENCES hotel (numero),
  cpf_cnpj integer NOT NULL,
  inicio timestamp not null,
  fim timestamp not null);

DROP TABLE IF EXISTS estadia cascade;
CREATE TABLE estadia (
  numero integer NOT NULL primary key,
  quarto text not null,
  inicio timestamp not null,
  fim timestamp,
  CONSTRAINT estadia_reserva_fk FOREIGN KEY
  (numero) REFERENCES reserva (numero)
  on delete restrict on update cascade);


CREATE OR REPLACE FUNCTION verifica_estadia() RETURNS trigger AS $$
DECLARE
    v_inicio timestamp;
    v_fim timestamp;
BEGIN
    SELECT inicio, fim INTO v_inicio,v_fim FROM reserva WHERE numero = NEW.numero;

    IF (NOT (NEW.inicio BETWEEN v_inicio AND (v_inicio + '1 day'::INTERVAL)
            AND NEW.fim BETWEEN v_inicio AND v_fim OR NEW.fim IS NULL
            AND NEW.inicio < NEW.fim OR NEW.fim IS NULL)) THEN
        RAISE EXCEPTION 'Erro: periodo de estadia invalido!';
    END IF;

    RETURN NEW;
END;$$ LANGUAGE plpgsql;

CREATE TRIGGER verifica_estadia_tgr BEFORE INSERT OR UPDATE ON estadia
FOR EACH ROW EXECUTE PROCEDURE verifica_estadia();


CREATE OR REPLACE FUNCTION verifica_reserva() RETURNS trigger AS $$
DECLARE
    v_inicio timestamp;
    v_fim timestamp;
BEGIN
    SELECT inicio, fim INTO v_inicio,v_fim FROM estadia WHERE numero = OLD.numero;

    IF (FOUND AND
        (NOT (v_inicio BETWEEN NEW.inicio AND (NEW.inicio + '1 day'::INTERVAL)
             AND v_fim BETWEEN NEW.inicio AND NEW.fim OR v_fim IS NULL
             AND NEW.inicio < NEW.fim))) THEN
        RAISE EXCEPTION 'Erro: periodo de estadia incompativel com reserva!';
    END IF;

    RETURN NEW;
END;$$ LANGUAGE plpgsql;

CREATE TRIGGER verifica_reserva_tgr BEFORE UPDATE ON reserva
FOR EACH ROW EXECUTE PROCEDURE verifica_reserva();

