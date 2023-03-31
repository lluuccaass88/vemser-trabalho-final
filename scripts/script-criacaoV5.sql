-- CRIAR TABELAS

CREATE TABLE CARGO(
	id_cargo NUMBER(10) NOT NULL,
	nome VARCHAR(100) UNIQUE NOT NULL,
	PRIMARY KEY(id_cargo)
);

CREATE TABLE USUARIO (
    
    id_usuario NUMBER(10) NOT NULL,
    nome VARCHAR2(100) NOT NULL,
    login VARCHAR2(100) UNIQUE NOT NULL,
    senha VARCHAR2(512) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    status VARCHAR2(100) NOT NULL,
    documento VARCHAR2(11) UNIQUE NOT NULL,
    PRIMARY KEY(id_usuario)
);

CREATE TABLE CARGO_X_USUARIO(
	id NUMBER(10) NOT NULL,
	id_cargo NUMBER(10) NOT NULL,
	PRIMARY KEY(id_usuario, id_cargo),
	CONSTRAINT FK_CARGO_USUARIO FOREIGN KEY (id_cargo) REFERENCES CARGO (id_cargo),
    CONSTRAINT FK_USUARIO_CARGO FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario)
);

CREATE TABLE CAMINHAO (
    id_caminhao NUMBER(10) NOT NULL,
    id_usuario NUMBER(10) NOT NULL,
    modelo VARCHAR2(100) NOT NULL,
    placa VARCHAR2(10) UNIQUE NOT NULL,
    nivel_combustivel NUMBER(3) NOT NULL,
    status_caminhao VARCHAR2(100) NOT NULL,
    status VARCHAR2(100) NOT NULL,
    PRIMARY KEY(id_caminhao),
    CONSTRAINT FK_USUARIO_CAMINHAO FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario)
);

CREATE TABLE ROTA (
    id_rota NUMBER(10) NOT NULL,
    id_usuario NUMBER(10) NOT NULL,
    descricao VARCHAR2(100) NOT NULL,
	local_partida VARCHAR2(100) NOT NULL,
	local_destino VARCHAR2(100) NOT NULL,
	status VARCHAR2(100) NOT NULL,
    PRIMARY KEY(id_rota),
    CONSTRAINT FK_USUARIO_ROTA FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario)
);

CREATE TABLE VIAGEM (
    id_viagem NUMBER(10) NOT NULL,
    id_usuario NUMBER(10) NOT NULL,
    id_caminhao NUMBER(10) NOT NULL,
    id_rota NUMBER(10) NOT NULL,
    descricao VARCHAR2(200) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    status_viagem VARCHAR2(100) NOT NULL,
    PRIMARY KEY(id_viagem),
    CONSTRAINT FK_USUARIO_VIAGEM FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario),
    CONSTRAINT FK_CAMINHAO_VIAGEM FOREIGN KEY (id_caminhao) REFERENCES CAMINHAO (id_caminhao),
    CONSTRAINT FK_ROTA_VIAGEM FOREIGN KEY (id_rota) REFERENCES ROTA (id_rota)
);


-- CRIAR SEQUENCES

CREATE SEQUENCE seq_usuario
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;

CREATE SEQUENCE seq_caminhao
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
CREATE SEQUENCE seq_rota
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;

CREATE SEQUENCE seq_viagem
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;

CREATE SEQUENCE seq_cargo
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;

