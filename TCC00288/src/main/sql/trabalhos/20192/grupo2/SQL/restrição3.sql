-- Restrição: Uma promoção nao poderá ser criada se uma categoria nao tiver produtos cadastrados
--
-- Tabelas utilizadas:
--   promocao
--   produto
--   produto_categoria

create or replace function confere_num_produtos_categoria()
returns trigger as $$
	declare
		c_num_prod_cat cursor for
			select count(*) as num_produtos from produto as p inner join produto_categoria as pc
			on p.produto_categoria_id = pc.id;
		c_promocao_cat cursor for
			select id from promocao as pr inner join produto_categoria as pc 
			on pr.produto_categoria_id == pc.id;
	begin
		delete from promocao where c_promocao_cat.id = promocao.id and c_num_prod_cat.num_produtos = 0;
		return old;
	end;
$$ language plpgsql;

create trigger restricao_categoria_promocao
after insert on promocao
for each row
execute procedure confere_num_produtos_categoria();
