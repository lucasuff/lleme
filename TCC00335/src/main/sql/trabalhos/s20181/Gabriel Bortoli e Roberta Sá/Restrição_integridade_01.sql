--TRIGGER 1 - Ao adicionar o item no carrinho, deve ser verificado se o item esta disponivel em estoque
-- para a quantidade adicionada e, se estiver, deve-se verificar se o mesmo esta em periodo promocional
-- para ser atualizado seu preco no carrinho. Caso sejam verdadeiras as proposicoes anteriores, o estoque
-- deverá ser atualizado com a quantia adicionada ao carrinho.

CREATE OR REPLACE FUNCTION adiciona_item_carrinho() RETURNS trigger AS $$

DECLARE
descont dec(10,2);
preco dec(10,2);

BEGIN

IF ((SELECT e.qnt 
	 FROM estoque e 
     WHERE NEW.produto_estoque = e.id_estoque) - NEW.qnt < 0
    ) THEN 

		RAISE EXCEPTION 'Desculpe! Esta loja nao possui quantidade suficiente deste produto em estoque!' ;

ELSE

		SELECT preco_unidade INTO preco FROM estoque
			WHERE estoque.id_estoque = new.produto_estoque;



	IF EXISTS ( SELECT desconto FROM promocao 
				WHERE promocao.estoque = NEW.produto_estoque 
				AND promocao.dia_ini < now() 
				AND promocao.dia_fim > now() 
			   ) THEN

		SELECT desconto INTO descont FROM promocao 
					WHERE promocao.estoque = NEW.produto_estoque 
					AND promocao.dia_ini < now() 
					AND promocao.dia_fim > now(); 

		NEW.preco := preco * ( 1 - ( descont/100));
        
       ELSE

               NEW.preco := preco;

	END IF;

	UPDATE estoque
	SET qnt = qnt - NEW.qnt
	WHERE NEW.produto_estoque = estoque.id_estoque;	
	
END IF;

RETURN NEW;

END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER trigger_adiciona_item_carrinho BEFORE INSERT ON item_carrinho
	FOR EACH ROW EXECUTE PROCEDURE adiciona_item_carrinho();
