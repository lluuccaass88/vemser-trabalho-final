package br.com.logisticadbc.exceptions;

import java.sql.SQLException;

public class BancoDeDadosException extends SQLException {

        public BancoDeDadosException(String message) {
            super(message);
        }
}
