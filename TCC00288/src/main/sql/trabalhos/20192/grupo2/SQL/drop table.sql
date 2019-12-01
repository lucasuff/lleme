-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2019-12-01 03:26:24.006

-- foreign keys
ALTER TABLE Promocao
    DROP CONSTRAINT Promocao_Produto_Categoria;

ALTER TABLE Compra
    DROP CONSTRAINT client_purchase;

ALTER TABLE Produto
    DROP CONSTRAINT product_category_product;

ALTER TABLE Produto_Categoria
    DROP CONSTRAINT product_category_product_category;

ALTER TABLE Compra_Produto
    DROP CONSTRAINT product_purchase_item;

ALTER TABLE Compra_Produto
    DROP CONSTRAINT purchase_purchase_item;

-- tables
DROP TABLE Cliente;

DROP TABLE Compra;

DROP TABLE Compra_Produto;

DROP TABLE Produto;

DROP TABLE Produto_Categoria;

DROP TABLE Promocao;

-- End of file.

