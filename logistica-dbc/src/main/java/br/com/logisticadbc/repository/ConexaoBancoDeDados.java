package br.com.logisticadbc.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ConexaoBancoDeDados {
    @Value("${bd.server}")
    private String server;
    @Value("${bd.database}")
    private String database;
    @Value("${bd.port}")
    private String port;

    // Configuração dos parâmetros de autenticação
    @Value("${bd.user}")
    private String user;

    @Value("${bd.password}")
    private String password;

    @Value("${bd.schema}")
    private String schema;

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + database;
        // abrindo a conexão com o banco de dados
        Connection connection = DriverManager.getConnection(url, user, password);
        // sempre usar o schema vem_ser
        connection.createStatement().execute("alter session set current_schema=" + schema);

        return connection;
    }
}