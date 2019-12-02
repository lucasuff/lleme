
CREATE OR REPLACE FUNCTION verifica_item() RETURNS trigger AS '
    BEGIN
      IF ((SELECT cnpj FROM pedido WHERE pedido_id = NEW.pedido_id) <> NEW.cnpj) THEN
        RAISE EXCEPTION $$Erro: prato % nao oferecido pelo restaurante!$$, NEW.prato_id;
      END IF;
      RETURN NEW;
    END;' LANGUAGE plpgsql;

CREATE TRIGGER verifica_item_tgr BEFORE INSERT OR UPDATE ON item_pedido
FOR EACH ROW EXECUTE PROCEDURE verifica_item();



CREATE OR REPLACE FUNCTION verifica_pedido() RETURNS trigger AS '
BEGIN
  IF (NEW.cnpj <> OLD.cnpj
      AND EXISTS (SELECT 1 FROM item_pedido WHERE pedido_id = OLD.pedido_id)) THEN
    RAISE EXCEPTION $$Erro: pedido ja possui itens de outro restaurante!$$;
  END IF;
  RETURN NEW;
END;' LANGUAGE plpgsql;

CREATE TRIGGER verifica_pedido_tgr BEFORE UPDATE ON pedido
  FOR EACH ROW EXECUTE PROCEDURE verifica_pedido();
