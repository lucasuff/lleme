

-- ATENÇÃO: entre cada teste, restaurar o banco de dados. Para restaurar, usar o arquivo BACKUP.tar e selecionar o papel "postgres".


-- RESTRIÇÃO 1
INSERT INTO public."Controlavel" VALUES (1056, 1, 1, 1, 6, 0, 500, 1, 100, 9001);


-- RESTRIÇÃO 2

UPDATE public."Controlavel" SET id_classe = 102 WHERE id = 1002;
-- Isso deve retirar o skill 1 do controlável 1002.

INSERT INTO public."Possui_Skill" VALUES (1048, 1);
-- 1048 é de uma classe que termina em 2, ou seja, não pode ter skill 1.

UPDATE public."Possui_Skill" SET id_skill = 200 WHERE id_skill = 1 AND id_controlavel = 1001;
-- A skill vai ser incom´patível com a classe, então o trigger deve impedir.

INSERT INTO public."Equipou_Skill" VALUES (1048, 1);
-- 1048 é de uma classe que termina em 2, ou seja, não pode equipar a skill 1.

UPDATE public."Equipou_Skill" SET id_skill = 200 WHERE id_skill = 1 AND id_controlavel = 1001;
-- A skill vai ser incom´patível com a classe, então o trigger deve impedir.

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

INSERT INTO public."Equipou_Skill" VALUES (1001, 200);
-- Skill 200 não pertence à classe do jogador 1001.

INSERT INTO public."Skill" VALUES (2000);
INSERT INTO public."Skill_Pertence_A_Classe" VALUES (2000, 100);
INSERT INTO public."Equipou_Skill" VALUES (1001, 2000);
-- A skill 2000 pertence à classe do controlável, mas o controlável não possui a skill


-- PROCESSAMENTO 1 & 2

UPDATE public."Controlavel" SET experiencia = (experiencia + 40001) WHERE id_controlavel = 1024;
-- Experiencia do 1024 aumenta o bastante para passar para o nível 20, 
-- que é também quando ele avança para a próxima classe.


-- PROCESSAMENTO 3

-- Uma missão não completada quando inserida não provoca mudanças.
INSERT INTO public."Realiza_Missao" VALUES (1024, 703, 'EM ANDAMENTO');
-- Uma missão completada inserida provoca mudanças.
INSERT INTO public."Realiza_Missao" VALUES (1024, 702, 'COMPLETADA');
-- Uma missão que passa de algum estado para completada provoca mudanças.
UPDATE public."Realiza_Missao" SET status = 'COMPLETADA' WHERE (id_controlavel = 1024) AND (id_missao = 703);
-- A mesma missão continua completada pelo mesmo personagem.
UPDATE public."Realiza_Missao" SET status = 'COMPLETADA' WHERE (id_controlavel = 1024) AND (id_missao = 703);
-- Mudando a missão ou o personagem, funciona como um INSERT.
UPDATE public."Realiza_Missao" SET id_controlavel = 1001, id_missao = 701 WHERE (id_controlavel = 1024) AND (id_missao = 703);
