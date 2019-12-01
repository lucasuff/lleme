-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2019-12-01 03:26:24.006

-- tables
-- Table: Cliente
CREATE TABLE Cliente (
    id int  NOT NULL,
    nome_completo varchar(255)  NOT NULL,
    email varchar(255)  NOT NULL,
    saldo real  NOT NULL,
    CONSTRAINT Cliente_pk PRIMARY KEY (id)
);

-- Table: Compra
CREATE TABLE Compra (
    id int  NOT NULL,
    cliente_id int  NOT NULL,
    data_compra date  NOT NULL,
    efetuada boolean  NOT NULL,
    preco_total real  NOT NULL,
    CONSTRAINT Compra_pk PRIMARY KEY (id)
);

-- Table: Compra_Produto
CREATE TABLE Compra_Produto (
    id int  NOT NULL,
    compra_id int  NOT NULL,
    produto_id int  NOT NULL,
    quantidade int  NOT NULL,
    preco real  NOT NULL,
    CONSTRAINT Compra_Produto_pk PRIMARY KEY (id)
);

-- Table: Produto
CREATE TABLE Produto (
    id int  NOT NULL,
    produto_categoria_id int  NOT NULL,
    nome varchar(255)  NOT NULL,
    preco real  NOT NULL,
    descricao varchar(1000)  NOT NULL,
    estoque int  NOT NULL,
    CONSTRAINT Produto_pk PRIMARY KEY (id)
);

-- Table: Produto_Categoria
CREATE TABLE Produto_Categoria (
    id int  NOT NULL,
    nome varchar(255)  NOT NULL,
    categoria_pai_id int  NULL,
    CONSTRAINT Produto_Categoria_pk PRIMARY KEY (id)
);

-- Table: Promocao
CREATE TABLE Promocao (
    id int  NOT NULL,
    porcentagem int  NOT NULL,
    inicio date  NOT NULL,
    fim date  NOT NULL,
    produto_categoria_id int  NOT NULL,
    CONSTRAINT Promocao_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: Promocao_Produto_Categoria (table: Promocao)
ALTER TABLE Promocao ADD CONSTRAINT Promocao_Produto_Categoria
    FOREIGN KEY (produto_categoria_id)
    REFERENCES Produto_Categoria (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: client_purchase (table: Compra)
ALTER TABLE Compra ADD CONSTRAINT client_purchase
    FOREIGN KEY (cliente_id)
    REFERENCES Cliente (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: product_category_product (table: Produto)
ALTER TABLE Produto ADD CONSTRAINT product_category_product
    FOREIGN KEY (produto_categoria_id)
    REFERENCES Produto_Categoria (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: product_category_product_category (table: Produto_Categoria)
ALTER TABLE Produto_Categoria ADD CONSTRAINT product_category_product_category
    FOREIGN KEY (categoria_pai_id)
    REFERENCES Produto_Categoria (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: product_purchase_item (table: Compra_Produto)
ALTER TABLE Compra_Produto ADD CONSTRAINT product_purchase_item
    FOREIGN KEY (produto_id)
    REFERENCES Produto (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: purchase_purchase_item (table: Compra_Produto)
ALTER TABLE Compra_Produto ADD CONSTRAINT purchase_purchase_item
    FOREIGN KEY (compra_id)
    REFERENCES Compra (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

