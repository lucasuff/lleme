-- Comando para zerar os dados
TRUNCATE TABLE public."Skill_Pertence_A_Classe",
	public."Possui_Skill",
	public."Equipou_Skill",
	public."Skill",
	public."Controlavel",
	public."Classe" ,
	public."Jogador" CASCADE;

-- Comandos pra popular o BD

INSERT INTO public."Jogador"
	(id)
VALUES	(9001),
		(9002),
		(9003),
		(9004);

INSERT INTO public."Classe" 
	(id, proxima_classe, nivel_proxima_classe)
VALUES	(100, 101, 20),
		(101, 102, 40),
		(102, NULL, NULL),
		(200, 201, 20),
		(201, 202, 40),
		(202, NULL, NULL);
		
INSERT INTO public."Controlavel" 
    (id, forca, agilidade, vitalidade,
    destreza, experiencia, dinheiro,
    nivel, id_classe, id_jogador) 
VALUES	(1001, 1, 2, 3, 4, 0, 0, 1, 100, 9001),
		(1002, 2, 3, 4, 5, 0, 0, 21, 101, 9001),
		(1024, 3, 4, 5, 6, 0, 0, 19, 200, 9002),
		(1048, 4, 5, 6, 7, 0, 0, 40, 202, 9003);

INSERT INTO public."Skill"
	(id)
VALUES	(1), -- todos têm isso antes de chegar numa classe que termina em 2
		(101),
		(102),
		(200),
		(1001),
		(1002),
		(1003),
		(1004),
		(1005);

INSERT INTO public."Skill_Pertence_A_Classe"
		(id_skill, id_classe)
VALUES	(1, 100),
		(1, 101),
		(1, 200),
		(1, 201),
		(101, 101),
		(101, 102),
		(102, 102),
		(200, 200),
		(200, 201),
		(200, 202),
		(1001, 100),
		(1002, 100),
		(1003, 100),
		(1004, 100),
		(1005, 100);

INSERT INTO public."Possui_Skill"
	(id_controlavel, id_skill)
VALUES	(1001, 1),
		(1002, 1),
		(1002, 101),
		(1024, 1),
		(1024, 200),
		(1048, 200);

INSERT INTO public."Equipou_Skill"
	(id_controlavel, id_skill)
VALUES	(1001, 1),
		(1002, 1),
		(1002, 101),
		(1024, 1),
		(1024, 200),
		(1048, 200);
		
		
INSERT INTO public."Missao"
	(id, experiencia, dinheiro)
VALUES	(701, 400, 10),
		(702, 401, 11),
		(703, 0, 0);

INSERT INTO public."Itens"
	(id)
VALUES	(1),
		(2),
		(3);

INSERT INTO public."Recompensa"
	(id_missao, id_item, quantidade)
VALUES	(702, 1, 1),
		(703, 2, 2),
		(703, 3, 3),
		(703, 1, 88);

INSERT INTO public."Possui_Itens"
	(id_controlavel, id_item, quantidade)
VALUES	(1001, 1, 99);

INSERT INTO public."Realiza_Missao"
	(id_controlavel, id_missao, status)
VALUES	(1001, 702, "COMPLETADA"),
		(1001, 701, "EM ANDAMENTO"),
		(1001, 703, "EM ANDAMENTO");