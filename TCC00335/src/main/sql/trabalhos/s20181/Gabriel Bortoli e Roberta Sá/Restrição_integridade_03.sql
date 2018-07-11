--TRIGGER 3 - VALIDAR SE LOJA ESTÁ EM HORARIO DE FUNCIONAMENTO

CREATE OR REPLACE FUNCTION loja_funcionando() RETURNS trigger AS $$
BEGIN
	IF ( (SELECT c.hora_pedido 
		  FROM carrinho c
		  WHERE c.id_carrinho = NEW.carrinho) < 
									(SELECT hora_abre 
									 FROM loja 
									 WHERE loja.id_loja = 
									 (SELECT loja 
									  FROM estoque 
									  WHERE NEW.produto_estoque = estoque.id_estoque)))
			 THEN 
             RAISE EXCEPTION 'Pedido feito antes da loja abrir!';
    	END IF;
        
	IF ((SELECT c.hora_pedido 
		 FROM carrinho c 
		 WHERE c.id_carrinho = NEW.carrinho) >
									(SELECT hora_fecha 
									 FROM loja 
									 WHERE loja.id_loja = 
									(SELECT loja 
									 FROM estoque 
									 WHERE NEW.produto_estoque = estoque.id_estoque)))
			THEN RAISE EXCEPTION 'Pedido feito com a loja ja fechada!';
	END IF;
	RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER loja_em_funcionamento BEFORE INSERT ON item_carrinho
FOR EACH ROW EXECUTE PROCEDURE loja_funcionando();