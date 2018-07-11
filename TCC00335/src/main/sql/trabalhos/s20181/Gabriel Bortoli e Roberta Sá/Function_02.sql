--FUNCTION 2 - Exibir os itens mais vendidos de cada loja e a total ja vendido deste mesmo item

create or replace function mais_vendido_loja() returns
	table(nome_loja character varying,
		   nome_produto character varying,
		   quant_vend int) as $$
declare
	r1 loja%rowtype;
	r2 estoque%rowtype;
	vend int;
	mais_vend int default 0;
	produt character varying default 'Nenhum produto vendido';
	
begin

	for r1 in select * from loja loop
		
		for r2 in select produto, id_estoque from estoque 
			where loja = r1.id_loja loop
			
				Select sum(item_carrinho.qnt) into vend from item_carrinho
                       		  inner join carrinho on item_carrinho.carrinho = carrinho.id_carrinho
                       		  where item_carrinho.produto_estoque = r2.id_estoque
                       		  and not carrinho.status = '%criado%'; 
			
			
				IF ( vend > mais_vend) then
				
					mais_vend := vend;
					select descricao into produt from produto where id_produto = r2.produto;
				
				end if;
			
		end loop;
		
		return query select r1.nome, produt, mais_vend;
		
	end loop;
end; $$ language 'plpgsql';
