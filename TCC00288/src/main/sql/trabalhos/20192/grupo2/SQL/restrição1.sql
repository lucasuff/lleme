-- Restrição: Saldo do cliente deve ser maior que o preço total da compra.
--
-- Tabelas utilizadas:
--	 cliente
--	 compra

create or replace function remove_compra_invalida() 
returns trigger as $$
	declare
		c_compra cursor for 
			select id from compra as cp inner join cliente as cl on cp.preco_total > cl.saldo;
	begin
		open c_compra;
		delete from compra where compra.id = c_compra.id and compra.id = old.id;
		return old;
		close c_compra;
	end;
$$ language plpgsql;

create trigger restricao_compra_saldo
after insert on compra
for each row
execute procedure remove_compra_invalida();
