package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.Posto;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class PostoRepository{
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT SEQ_POSTO.NEXTVAL mysequence FROM DUAL";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao buscar sequence de posto" + e);
        }
    }

    public Posto adicionar(Posto posto) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(con);
            posto.setIdPosto(proximoId);

            String sql = "INSERT INTO POSTO\n" +
                    "(ID_POSTO, NOMEPOSTO, VALORCOMBUSTIVEL)\n" +
                    "VALUES(?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, posto.getIdPosto());
            stmt.setString(2, posto.getNomePosto());
            stmt.setDouble(3, posto.getValorCombustível());

            int res = stmt.executeUpdate();

            if (res == 0) {
                throw new BancoDeDadosException("Erro ao adicionar posto");
            } else {
//                System.out.println("Posto adicionada com sucesso!" +
//                        "\nadicionarPosto.res=" + res);
                System.out.println("Posto adicionada com sucesso!");
            }
            return posto;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao adicionar posto" + e);
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

    // removerPosto_X_Rota conforme está descrito na tabela
    public boolean removerPostoXRota(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM ROTA_X_POSTO WHERE ID_POSTO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            if (res == 0) {
                throw new BancoDeDadosException("Erro ao remover posto");
            } else {
//                System.out.println("Relacionamento de rota com posto removida com sucesso!" +
//                        "\nremoverRotaPorId.res=" + res);
                System.out.println("Relacionamento de rota com posto removida com sucesso!");
                return res > 0;
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao remover o relacionamento de rota e posto" + e);
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
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM POSTO WHERE ID_POSTO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            if (res == 0) {
                throw new BancoDeDadosException("Erro ao remover posto");
            } else {
                System.out.println("Posto removida com sucesso!" +
                        "\nremoverPostoPorId.res=" + res);
                return res > 0;
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao remover posto" + e);
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


    public boolean editar(Integer id, Posto posto) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE POSTO SET ");
            sql.append("NOMEPOSTO = ?, ");
            sql.append("VALORCOMBUSTIVEL = ? ");
            sql.append("WHERE ID_POSTO =  ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, posto.getNomePosto());
            stmt.setDouble(2, posto.getValorCombustível());
            stmt.setInt(3, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();

            if (res == 0) {
                throw new BancoDeDadosException("Erro ao editar posto.");
            } else {
                System.out.println("Posto editado com sucesso!" +
                        "\neditarPostoPorId.res=" + res);
                return res > 0;
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao editar posto: " + e);
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


    public List<Posto> listar() throws BancoDeDadosException {
        List<Posto> postos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM POSTO";

            PreparedStatement stmt = con.prepareStatement(sql);
            // Executa-se a consulta
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Posto posto = new Posto();
                posto.setIdPosto(rs.getInt("ID_POSTO"));
                posto.setNomePosto(rs.getString("NOMEPOSTO"));
                posto.setValorCombustível(rs.getDouble("VALORCOMBUSTIVEL"));
                postos.add(posto);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao listar postos" + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return postos;
    }

    public Posto buscarPorId(int id)throws BancoDeDadosException{
        Connection con = null;
        Posto posto = new Posto();

        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM POSTO WHERE ID_POSTO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            // Executa-se a consulta
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                posto.setIdPosto(rs.getInt("ID_POSTO"));
                posto.setNomePosto(rs.getString("NOMEPOSTO"));
                posto.setValorCombustível(rs.getDouble("VALORCOMBUSTIVEL"));
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao buscas o postos" + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return posto;
    }
}
