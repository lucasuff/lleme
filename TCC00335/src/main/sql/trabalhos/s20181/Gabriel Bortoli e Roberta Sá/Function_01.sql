--FUNCTION 1 - Exibir os total de itens vendidos de cada produto e a soma total da venda

create or replace function total_vendido_produto() returns
	table(ident_p int,
		qnd int,
		total dec(10,2)) as $$
declare
	c_cp cursor (idt int) for select * from item_carrinho where produto_estoque=idt for share of item_carrinho;
	r1 estoque%rowtype;
	r2 item_carrinho%rowtype;
	qnd int;
	total dec(10,2) default '0';
begin
	for r1 in select id_produto from produto loop
		qnd = 0;
		total = 0.00;
		for r2 in c_cp(r1.id_estoque) loop
			qnd = qnd + r2.qnt;
            total =  total + (r2.preco_unidade * r2.qnt);
		end loop;
		return query select r1.id_estoque, qnd, total;
	end loop;
end; $$ language 'plpgsql';