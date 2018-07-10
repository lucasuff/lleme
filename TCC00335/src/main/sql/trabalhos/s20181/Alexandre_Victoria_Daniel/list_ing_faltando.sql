CREATE OR REPLACE FUNCTION list_ing_faltando(receita_id INTEGER, usuario_endereco_id INTEGER) RETURNS TABLE( id BIGINT, nome CHARACTER VARYING ) AS $BODY$
	BEGIN
	RETURN QUERY WITH	
	Ing_rec AS(SELECT ingrediente_id FROM receita_ingrediente recIng WHERE receita_id = recIng.receita_id)
	
	SELECT 
			t1.id, t2.nome 
		FROM Ing_rec t1 INNER JOIN Ingrediente t2 ON t1.id = t2.id
		WHERE 
			id NOT IN (SELECT ig.ingrediente_id AS id
						FROM Receita_Ingrediente AS ig 
						JOIN estoque AS eq ON eq.ingrediente_id = ig.id 
						JOIN endereco AS ed ON ed.id = eq.endereco_id 
						JOIN usuario_endereco AS ue ON ue.endereco_id = ed.id
						WHERE ue.id = usuario_endereco_id AND receita_id = ig.receita_id)
	
	END;
	