create table estado (
	estado_sigla varchar(2) primary key,
	nome varchar(20) NOT NULL
);

create table cidade(
	cidade_id serial primary key,
	nome varchar(50) NOT NULL,
	estado_sigla varchar(2) references estado(estado_sigla) NOT NULL,
	UNIQUE (estado_sigla, nome)
);

create table bairro(
	bairro_id serial primary key,
	cidade_id int references cidade(cidade_id) NOT NULL,
	nome varchar(50) NOT NULL,
	UNIQUE (cidade_id,nome)
);

create table faculdade(
	faculdade_id serial primary key,
	nome varchar(200) NOT NULL,
	sigla varchar(10) NOT NULL
);

create table campus(
	campus_id serial primary key,
	faculdade_id int references faculdade(faculdade_id),
	bairro_id int references bairro(bairro_id),
	nome varchar(100) NOT NULL,
	UNIQUE(nome,faculdade_id)
);

create table usuario(
	usuario_id serial primary key,
	nome varchar(300) NOT NULL,
	whatsapp varchar(11) NOT NULL,
	email varchar(100) UNIQUE NOT NULL,
	faculdade_id int references faculdade(faculdade_id)
);

create table carro(
	carro_id serial primary key,
	placa varchar(7) UNIQUE,
	modelo varchar(50) NOT NULL,
	cor varchar(20) NOT NULL,
	usuario_id int references usuario(usuario_id) NOT NULL
);

CREATE TYPE carona_status AS ENUM ('ABERTA', 'FECHADA', 'INICIADA','REALIZADA','CANCELADA');

create table carona(
	carona_id serial primary key,
	usuario_id int references usuario(usuario_id) NOT NULL,
	carro_id int references carro(carro_id) NOT NULL,
	hora_saida timestamp NOT NULL,
	valor int NOT NULL,
	vagas_qtd int NOT NULL,
	bairro_id int references bairro(bairro_id) NOT NULL,
	campus_id int references campus(campus_id) NOT NULL,
	indo_para_faculdade bool NOT NULL,
	carona_status carona_status NOT NULL,
	criado_em timestamp DEFAULT now()
);

create table carona_participa(
	carona_id int references carona(carona_id) NOT NULL,
	usuario_id int references usuario(usuario_id) NOT NULL,
	avaliacao int,
	primary key(usuario_id, carona_id)
);

create table carona_interesse(
	carona_id int references carona(carona_id) NOT NULL,
	usuario_id int references usuario(usuario_id) NOT NULL,
    criado_em timestamp DEFAULT now(),
	primary key(usuario_id, carona_id)
);

create table usuario_avaliacao(
	usuario_id int references usuario(usuario_id),
	avaliacao_media real,
	qtd_avaliacao int
)