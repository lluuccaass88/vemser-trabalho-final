
-- CRIAR TABELAS

CREATE TABLE COLABORADOR (
    
    id_usuario NUMBER(10) NOT NULL,
    nome VARCHAR2(100) NOT NULL,
    usuario VARCHAR2(100) UNIQUE NOT NULL,
    senha VARCHAR2(50) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    status_usuario NUMBER(1) NOT NULL,
    cpf VARCHAR2(11) UNIQUE NOT NULL,
    PRIMARY KEY(id_usuario)
   
);

CREATE TABLE MOTORISTA (
    
    id_usuario NUMBER(10) NOT NULL,
    nome VARCHAR2(100) NOT NULL,
    usuario VARCHAR2(100) UNIQUE NOT NULL,
    senha VARCHAR2(50) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    status_usuario NUMBER(1) NOT NULL,
    cnh VARCHAR2(11) UNIQUE NOT NULL,
    status_motorista NUMBER(1) NOT NULL,
    PRIMARY KEY(id_usuario)
   
);

CREATE TABLE CAMINHAO (
    id_caminhao NUMBER(10) NOT NULL,
    id_usuario NUMBER(10) NOT NULL,
    modelo VARCHAR2(100) NOT NULL,
    placa VARCHAR2(10) UNIQUE NOT NULL,
    nivel_combustivel NUMBER(3) NOT NULL,
    status_caminhao NUMBER(1) NOT NULL,
    PRIMARY KEY(id_caminhao),
    CONSTRAINT FK_COLABORADOR_CAMINHAO FOREIGN KEY (id_usuario) REFERENCES COLABORADOR (id_usuario)
);

CREATE TABLE ROTA (
    id_rota NUMBER(10) NOT NULL,
    id_usuario NUMBER(10) NOT NULL,
    descricao VARCHAR2(100) NOT NULL,
	local_partida VARCHAR2(100) NOT NULL,
	local_destino VARCHAR2(100) NOT NULL,
    PRIMARY KEY(id_rota),
    CONSTRAINT FK_COLABORADOR_ROTA FOREIGN KEY (id_usuario) REFERENCES COLABORADOR (id_usuario)
);

CREATE TABLE POSTO (
    id_posto NUMBER(10) NOT NULL,
    id_usuario NUMBER(10) NOT NULL,
    nome VARCHAR2(100) NOT NULL,
    valor_combustivel NUMBER(4) NOT NULL,
    PRIMARY KEY(id_posto),
    CONSTRAINT FK_COLABORADOR_POSTO FOREIGN KEY (id_usuario) REFERENCES COLABORADOR (id_usuario)
);

CREATE TABLE ROTA_X_POSTO (
	id_rota NUMBER(10) NOT NULL,
    id_posto NUMBER(10) NOT NULL,
    PRIMARY KEY(id_rota, id_posto),
    CONSTRAINT FK_ROTA_POSTO FOREIGN KEY (id_rota) REFERENCES ROTA (id_rota),
    CONSTRAINT FK_POSTO_ROTA FOREIGN KEY (id_posto) REFERENCES POSTO (id_posto)
);

CREATE TABLE VIAGEM (
    id_viagem NUMBER(10) NOT NULL,
    id_usuario NUMBER(10) NOT NULL,
    id_caminhao NUMBER(10) NOT NULL,
    id_rota NUMBER(10) NOT NULL,
    descricao VARCHAR2(200) NOT NULL,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,
    status_viagem NUMBER(1) NOT NULL,
    PRIMARY KEY(id_viagem),
    CONSTRAINT FK_MOTORISTA_VIAGEM FOREIGN KEY (id_usuario) REFERENCES MOTORISTA (id_usuario),
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

CREATE SEQUENCE seq_posto
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;

CREATE SEQUENCE seq_viagem
 START WITH     1
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;


