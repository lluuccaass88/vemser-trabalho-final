package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.Perfil;
import br.com.logisticadbc.entity.Usuario;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class UsuarioRepository {

    private final ConexaoBancoDeDados conexaoBancoDeDados;

    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT SEQ_USUARIO.NEXTVAL mysequence FROM DUAL";

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
            con = conexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(con);
            usuario.setId(proximoId);

            String sql = "INSERT INTO USUARIO\n" +
                    "(ID_USUARIO, NOME, USUARIO, SENHA, PERFIL, CPF, CNH, EMAIL)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getUsuario());
            stmt.setString(4, usuario.getSenha());
            stmt.setInt(5, usuario.getPerfil().getPerfil());
            stmt.setString(6, usuario.getCpf());
            stmt.setString(7, usuario.getCnh());
            stmt.setString(8, usuario.getEmail());

            int res = stmt.executeUpdate();
            if (res > 0) {
                return usuario;
            } else {
                throw new BancoDeDadosException("Erro ao adicionar Usuário");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Erro ao adicionar Usuário ");
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

    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM USUARIO WHERE ID_USUARIO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            int res = stmt.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Erro ao remover Usuário ");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean editar(Integer id, Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE USUARIO SET ");
            sql.append(" NOME = ?, ");
            sql.append(" USUARIO = ?, ");
            sql.append(" SENHA = ?, ");
            sql.append(" PERFIL = ?, ");
            sql.append(" CPF = ?, ");
            sql.append(" CNH = ?, ");
            sql.append(" EMAIL = ? ");
            sql.append(" WHERE ID_USUARIO = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getPerfil().getPerfil());
            stmt.setString(5, usuario.getCpf());
            stmt.setString(6, usuario.getCnh());
            stmt.setString(7, usuario.getEmail());
            stmt.setInt(8, id);

            int res = stmt.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Erro ao editar usuário");
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

    public List<Usuario> listar() throws BancoDeDadosException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM USUARIO"; // Consulta SQL no banco

            ResultSet rs = stmt.executeQuery(sql); // Executa-se a consulta

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("ID_USUARIO"));
                usuario.setNome(rs.getString("NOME"));
                usuario.setUsuario(rs.getString("USUARIO"));
                usuario.setSenha(rs.getString("SENHA"));
                usuario.setPerfil(Perfil.ofTipoPerfil(rs.getInt("PERFIL")));
                usuario.setCpf(rs.getString("CPF"));
                usuario.setCnh(rs.getString("CNH"));
                usuario.setEmail(rs.getString("EMAIL"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Erro ao listar Usuários ");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuarios;
    }

    public Usuario login(String usurario, String senha) throws BancoDeDadosException {
        Usuario usuario = new Usuario();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM USUARIO u \n" +
                    "\tWHERE u.USUARIO = ? AND u.SENHA = ?"; // Consulta SQL no banco
            PreparedStatement stmt = con.prepareStatement(sql); // Prepara a consulta

            stmt.setString(1, usurario);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery(); // Executa-se a consulta

            while (rs.next()) {
                usuario.setId(rs.getInt("ID_USUARIO"));
                usuario.setNome(rs.getString("NOME"));
                usuario.setUsuario(rs.getString("USUARIO"));
                usuario.setSenha(rs.getString("SENHA"));
                usuario.setPerfil(Perfil.ofTipoPerfil(rs.getInt("PERFIL")));
                usuario.setCpf(rs.getString("CPF"));
                usuario.setCnh(rs.getString("CNH"));
                usuario.setEmail(rs.getString("EMAIL"));
            }
            return usuario;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao listar Usuários " + e);
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