--TRIGGER 2 - Ao alterar Status do carrinho para que a loja inicie a separacao do pedido, devera ser
-- verificado se o mesmo esta vazio, e, caso nao esteja, devera ser verificado se os produtos inseridos 
-- sao da mesma loja

CREATE OR REPLACE FUNCTION validar_mesma_loja()
  RETURNS trigger AS $$  
  BEGIN

IF NOT EXISTS ( SELECT * FROM item_carrinho 
				WHERE item_carrinho.carrinho = NEW.id_carrinho  
			   ) THEN

	RAISE EXCEPTION 'Seu carrinho está vazio.';

ELSE

	IF ((SELECT COUNT(DISTINCT loja) 
		 FROM estoque 
		INNER JOIN item_carrinho 
		 		ON estoque.id_estoque = item_carrinho.produto_estoque
				AND item_carrinho.carrinho = NEW.id_carrinho)>1)  THEN

	RAISE EXCEPTION 'Seu carrinho possui itens de lojas distintas.';

	END IF;
	
END IF;

RETURN NEW;

END
$$
LANGUAGE plpgsql;
  
CREATE TRIGGER trigger_validar_mesma_loja
  BEFORE UPDATE OF status
  ON carrinho
  FOR EACH ROW
  EXECUTE PROCEDURE validar_mesma_loja();