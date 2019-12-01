create or replace function efetuar_compra(cli int)
returns void as $$
	declare 
		c_comp cursor for select * from compra where cliente_id = cli;
		r_cliente cliente%rowtype;
		r_comp compra%rowtype;
		r_comp_pro produto%rowtype;
		r_prod produto%rowtype;
	begin
		select * from cliente as cl into r_cliente where cliente.id = cli;
		for r_comp in c_comp loop
			if (not r_comp.efetuada) then
				r_cliente.saldo = r_cliente.saldo - r_comp.valor total;
				for r_comp_pro in select * from compra_produto where compra_id = r_comp.id loop
					select produto as pd into r_prod where pd.id = r_comp_pro.produto_id;
					r_prod.estoque = estoque - r_comp_pro.quantidade;
				end loop;
				r_comp.efetuada = true;
			end if;
		end loop;			
	end;
$$ language plpgsql;
