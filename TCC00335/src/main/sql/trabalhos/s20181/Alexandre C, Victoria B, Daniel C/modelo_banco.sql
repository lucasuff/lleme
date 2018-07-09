DROP TABLE IF EXISTS Refeicao;
DROP TABLE IF EXISTS Usuario_Endereco;
DROP TABLE IF EXISTS Restricao;
DROP TABLE IF EXISTS Estoque;
DROP TABLE IF EXISTS Endereco;
DROP TABLE IF EXISTS Receita_Ingrediente;
DROP TABLE IF EXISTS Ingrediente;
DROP TABLE IF EXISTS Receita;
DROP TABLE IF EXISTS Usuario;

CREATE TABLE Usuario(
	id bigserial primary key,
	nome varchar(255) NOT NULL
);

CREATE TABLE Ingrediente(
	id bigserial primary key,
	nome varchar(255) NOT NULL
);

CREATE TABLE Endereco(
	id bigserial primary key,
	logradouro varchar(255) NOT NULL,
	numero int NOT NULL
);

CREATE TABLE Usuario_Endereco(
	id bigserial primary key,
	usuario_id int,
	endereco_id int,
	CONSTRAINT usuario_id FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
	CONSTRAINT endereco_id FOREIGN KEY (endereco_id) REFERENCES Endereco(id)
);

CREATE TABLE Restricao(
	id bigserial primary key,
	usuario_id int,
	ingrediente_id int,
	CONSTRAINT usuario_id_restricao FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
	CONSTRAINT ingrediente_id_restricao FOREIGN KEY (ingrediente_id) REFERENCES Ingrediente(id)
);

CREATE TABLE Receita(
	id bigserial primary key,
	nome varchar(255),
	usuario_id int,
	CONSTRAINT usuario_id_receita FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

CREATE TABLE Receita_Ingrediente(
	id bigserial primary key,
	receita_id int,
	ingrediente_id int,
	CONSTRAINT receita_ingrediente_fk_receita FOREIGN KEY (receita_id) REFERENCES Receita(id),
	CONSTRAINT receita_ingrediente_fk_ingrediente FOREIGN KEY (receita_id) REFERENCES Ingrediente(id)
);

CREATE TABLE Refeicao(
	id bigserial primary key,
	receita_id int,
	usuario_endereco_id int,
	CONSTRAINT refeicao_id FOREIGN KEY (receita_id) REFERENCES Receita(id),
	CONSTRAINT usuario_endereco_id FOREIGN KEY (usuario_endereco_id) REFERENCES Receita_Ingrediente(id)

);

CREATE TABLE Estoque(
	id bigserial primary key,
	quantidade int,
	endereco_id int,
	ingrediente_id int,
	CONSTRAINT endereco_id_estoque FOREIGN KEY (endereco_id) REFERENCES Endereco(id),
	CONSTRAINT ingrediente_id_estoque FOREIGN KEY (ingrediente_id) REFERENCES Ingrediente(id)
)