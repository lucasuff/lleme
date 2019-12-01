-- Restrição: Um produto deve estar em estoque para poder ser comprado.
-- Se um produto não ter estoque o suficiente, a compra é cancelada.
--
-- Tabelas utilizadas:
--   produto
--   compra_produto
--   compra

create or replace function confere_estoque_e_remove()
returns trigger as $$
	declare
		c_compra_produto cursor for
			select id from compra_produto as cp inner join produto as pr
			on cp.produto_id = pr.id and cp.quantidade > produto.estoque;
		rec_compra record;
	begin
		open c_compra_produto;
		select id into rec_compra from compra as cm natural join c_compra_produto;
		delete from compra where compra.id = rec_compra.id and compra.id = old.id;
		return old;
		close c_compra_produto;
	end;
$$ language plpgsql;

create trigger restricao_compra_estoque
after insert on compra
for each row
execute procedure confere_estoque_e_remove();
