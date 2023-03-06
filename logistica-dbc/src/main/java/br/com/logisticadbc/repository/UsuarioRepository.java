package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.Usuario;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UsuarioRepository {

    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT LOGISTICA.SEQ_USUARIO.NEXTVAL mysequence FROM DUAL";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao buscar sequence de Usuário " + e);
        }
    }

    public Usuario adicionar(Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(con);
            usuario.setId(proximoId);

            String sql = "INSERT INTO USUARIO\n" +
                    "(ID_USUARIO, NOME, USUARIO, SENHA, PERFIL, CPF, CNH)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getUsuario());
            stmt.setString(4, usuario.getSenha());
            stmt.setInt(5, usuario.getPerfil().getPerfil());
            stmt.setString(6, usuario.getCpf());
            stmt.setString(7, usuario.getCnh());

            int res = stmt.executeUpdate();
            if (res > 0) {
                return usuario;
            } else {
                throw new BancoDeDadosException("Erro ao adicionar Usuário");
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao adicionar Usuário " + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}