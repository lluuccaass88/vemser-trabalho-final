package br.com.logisticadbc.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {
    private static final String SERVER = "vemser-dbc.dbccompany.com.br";
    private static final String DATABASE = "xe";
    private static final String PORT = "25000"; // porta disponibilzada pelo servidor da DBC

    // Configuração dos parâmetros de autenticação
    private static final String USER = "LOGISTICA";
    private static final String PASSWORD = "UozdFoKcSiLn";
    private static final String SCHEMA = "VEM_SER";

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;

        // abrindo a conexão com o banco de dados
        Connection connection = DriverManager.getConnection(url, USER, PASSWORD);

        // sempre usar o schema vem_ser
        connection.createStatement().execute("alter session set current_schema=" + SCHEMA);

        return connection;
    }
}
