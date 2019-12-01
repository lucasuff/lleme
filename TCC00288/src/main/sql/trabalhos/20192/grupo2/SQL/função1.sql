-- Função: atualiza o valor de uma compra em um carrinho (compra_produto) se a categoria daquele produto estiver em promoção
--
-- Tabelas utilizadas:
--	 compra_produto
--	 produto_categoria
--	 produto

create or replace function confere_promocao()
returns void as $$
	declare
		promo cursor for select * from compra_produto as cp join produto as pd
		on cp.produto_id = pd.id join produto_categoria as pc
		on pd.categoria_id = pc.id join promocao as pr on pc.id = pr.produto_categoria_id;
		sales compra_produto%rowtype;
		
	begin
		for sales in select * from compra_produto loop
			for thispromo in promo loop 
				if(sales.id = thispromo.id and thispromo.inicio >= now() and thispromo.fim<=now()) then
					update sales set sales.preco = sales.preco*promo.porcentagem;
				end if;
			end loop;
		end loop;
	end;
$$ language plpgsql;