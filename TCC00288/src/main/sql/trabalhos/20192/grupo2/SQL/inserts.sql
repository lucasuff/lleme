INSERT INTO cliente VALUES (1, 'Rafael Dantas Amancio', 'rafael@uff.br', 800.00);
INSERT INTO cliente VALUES (2, 'Marcelo José Medeiros', 'mjm@uff.br', 4000.00);
INSERT INTO cliente VALUES (3, 'João Gabriel Machado', 'jgm@uff.br', 3000.00);
INSERT INTO cliente VALUES (4, 'Lucas de Castro Lopes', 'lucas@uff.br', 1000.00);
INSERT INTO cliente VALUES (5, 'Eduardo Martins', 'eduardo@uff.br', 200.00);
INSERT INTO cliente VALUES (6, 'Luis André Paes Leme', 'lleme@uff.br', 1500.00);

INSERT INTO produto_categoria VALUES (1, 'Eletrônicos');
INSERT INTO produto_categoria VALUES (2, 'Smartphones', '1');
INSERT INTO produto_categoria VALUES (3, 'Vestuário');
INSERT INTO produto_categoria VALUES (4, 'Alimentação');
INSERT INTO produto_categoria VALUES (5, 'TVs', '1');
INSERT INTO produto_categoria VALUES (6, 'Notebooks', '1');

INSERT INTO produto VALUES (1, '2','Samsung Galaxy S10', 4000.00, 'Smartphone Samsung', 18);
INSERT INTO produto VALUES (2, '3','Camisa Polo Verde M', 40.00, 'Camisa Verde', 8);
INSERT INTO produto VALUES (3, '3','Short Cinza P', 30.00, 'Short', 2);
INSERT INTO produto VALUES (4, '5','Smart TV Panasonic 43"', 3000.00, 'TV Panasonic', 30);
INSERT INTO produto VALUES (5, '2','iPhone XS Max', 7000.00, 'Apple iPhone XS Max', 22);
INSERT INTO produto VALUES (6, '4','Leite Elegê', 2.50, 'Leite Elegê', 1);
INSERT INTO produto VALUES (7, '3','Óculos Rayban', 500.00, 'Óculos', 2);
INSERT INTO produto VALUES (8, '2','Moto G7 Plus', 1200.00, 'Smartphone Motorola', 3);
INSERT INTO produto VALUES (9, '6','Samsung Essentials E32', 1500.00, 'Notebook Samsung 14 polegadas 1TB 4GB', 5);
INSERT INTO produto VALUES (10, '6','MacBook Air 2019', 9000.00, 'Notebook Apple 13 polegadas', 2);

INSERT INTO compra VALUES (1, 1, '2019-10-20', true, 500.00);
INSERT INTO compra VALUES (2, 1, '2019-10-21', true, 1500.00);
INSERT INTO compra VALUES (3, 2, '2019-10-22', true, 2500.00);
INSERT INTO compra VALUES (4, 2, '2019-10-23', true, 4500.00);
INSERT INTO compra VALUES (5, 4, '2019-10-24', true, 700.00);

INSERT INTO promocao VALUES (1, 75, '2019-10-24', '2019-11-24', 2);
INSERT INTO promocao VALUES (2, 50, '2019-09-15', '2019-11-15', 3);
