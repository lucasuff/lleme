DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS produto;
DROP TABLE IF EXISTS loja;
DROP TABLE IF EXISTS estoque;
DROP TABLE IF EXISTS carrinho;
DROP TABLE IF EXISTS item_carrinho;
DROP TABLE IF EXISTS entrega;
DROP TABLE IF EXISTS pagamento;

CREATE TABLE cliente(
    id_cliente      serial    		    NOT NULL,
    login           CHARACTER VARYING       NOT NULL,
    senha           CHARACTER VARYING       NOT NULL,
    tel             CHARACTER VARYING               ,
    endereco        CHARACTER VARYING               ,
    CONSTRAINT      PK_cliente PRIMARY KEY(id_cliente),
    CONSTRAINT      SK_cliente UNIQUE(login, senha)
);


CREATE TABLE produto(
    id_produto      serial                  NOT NULL,
    descricao       CHARACTER VARYING       NOT NULL,
    CONSTRAINT      PK_produto PRIMARY KEY(id_produto)
);


CREATE TABLE loja(
    id_loja         serial                 NOT NULL,
    nome	    CHARACTER VARYING 	   NOT NULL,
    end_loja        CHARACTER VARYING      NOT NULL,
    hora_abre       TIME                   NOT NULL,
    hora_fecha      TIME                   NOT NULL,
    CONSTRAINT      PK_loja PRIMARY KEY(id_loja)
);


CREATE TABLE estoque(
    id_estoque      serial                 NOT NULL,
    produto	    INT			   NOT NULL,
    loja            INT			   NOT NULL,
    qnt             INT		           	   ,
    preco_unidade   DEC(10,2)			   ,
    CONSTRAINT      PK_estoque PRIMARY KEY(id_estoque)  ,
    CONSTRAINT      SK_estoque UNIQUE(produto,loja)	   ,
    CONSTRAINT      FK1_estoque FOREIGN KEY(produto) REFERENCES produto(id_produto),
    CONSTRAINT      FK2_estoque FOREIGN KEY(loja)    REFERENCES loja(id_loja)
);


CREATE TABLE carrinho(
    id_carrinho     serial               NOT NULL,
    cliente         INT			 NOT NULL,
    hora_pedido	    TIME		 NOT NULL,
    hora_delivery   TIME                         ,
    status          CHARACTER VARYING    DEFAULT 'criado',
    CONSTRAINT      PK_carrinho PRIMARY KEY(id_carrinho) ,
    CONSTRAINT      FK2_carrinho FOREIGN KEY(cliente) REFERENCES Cliente(id_cliente)
);


CREATE TABLE item_carrinho(
    id_item_carrinho serial         	  NOT NULL,
    carrinho	     INT            	  NOT NULL,
    produto_estoque  INT            	  NOT NULL,
    qnt              INT	      	  NOT NULL,
    preco            DEC(10,2)      	  DEFAULT '0',
    CONSTRAINT       PK_item_carrinho PRIMARY KEY (id_item_carrinho),
    CONSTRAINT       FK1_item_carrinho FOREIGN KEY(carrinho) REFERENCES carrinho(id_carrinho),
    CONSTRAINT       FK3_item_carrinho FOREIGN KEY(produto_estoque) REFERENCES estoque(id_estoque)
);


CREATE TABLE entrega(
    id_entrega      serial           NOT NULL   ,
    carrinho        INT              NOT NULL   ,
    stat            BOOL	     DEFAULT 'f',
    momento_saida   TIMESTAMP                   ,
    momento_chegada TIMESTAMP                   ,
    CONSTRAINT      PK_Entrega PRIMARY KEY(id_entrega),
    CONSTRAINT      FK1_Entrega FOREIGN KEY(carrinho) REFERENCES carrinho(id_carrinho)
);


CREATE TABLE pagamento(
    carrinho        INT		     NOT NULL,
    momento_pag     TIMESTAMP        NOT NULL,
    CONSTRAINT 	    PK_Pagamento PRIMARY KEY(carrinho),
    CONSTRAINT      FK_Pagamento FOREIGN KEY(carrinho) REFERENCES carrinho(id_carrinho)
);


CREATE TABLE promocao(
    id_promocao     serial 	     NOT NULL,
    estoque 	    integer 	     NOT NULL,
    desconto 	    integer 	     NOT NULL,
    dia_ini 	    date  	     NOT NULL,
    dia_fim 	    date 	     NOT NULL,
    CONSTRAINT 	    pk_promocao PRIMARY KEY (id_promocao),
    CONSTRAINT 	    sk_promocao UNIQUE (estoque, dia_ini, dia_fim),
    CONSTRAINT 	    fk_promocao FOREIGN KEY (estoque) REFERENCES estoque(id_estoque) 
);