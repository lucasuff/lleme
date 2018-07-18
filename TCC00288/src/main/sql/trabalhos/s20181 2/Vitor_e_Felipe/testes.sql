

-- ATENÇÃO: entre cada teste, restaurar o banco de dados.


-- RESTRIÇÃO 1
INSERT INTO public."Controlavel" VALUES (1056, 1, 1, 1, 6, 0, 500, 1, 100, 9001);


-- RESTRIÇÃO 2

UPDATE public."Controlavel" SET id_classe = 102 WHERE id = 1002;
-- Isso deve retirar o skill 1 do controlável 1002.

INSERT INTO public."Possui_Skill" VALUES (1048, 1);
-- 1048 é de uma classe que termina em 2, ou seja, não pode ter skill 1.

UPDATE public."Possui_Skill" SET id_skill = 200 WHERE id_skill = 1 AND id_controlavel = 1001;
-- 1001 deve perder a skill 1 e não pode ganhar a skill 200.

DELETE FROM public."Skill_Pertence_A_Classe" WHERE id_skill = 200 AND id_classe = 200;
-- 1024 pertence à classe 200 e tem o skill 200, que deve ser perdido.


-- RESTRIÇÃO 3

INSERT INTO public."Possui_Skill" 
VALUES	(1001, 1001), 
		(1001, 1002), 
		(1001, 1003),
		(1001, 1004),
		(1001, 1005);
INSERT INTO public."Equipou_Skill" 
VALUES	(1001, 1001), 
		(1001, 1002), 
		(1001, 1003),
		(1001, 1004),
		(1001, 1005);
-- 1001 já tinha o skills 1 equipado, então equipar o skill 1005 deve falhar.


-- PROCESSAMENTO 1 & 2

UPDATE public."Controlavel" SET experiencia = (experiencia + 40001) WHERE id_controlavel = 1024;
-- Experiencia do 1024 aumenta o bastante para passar para o nível 20, 
-- que é também quando ele avança para a próxima classe.
