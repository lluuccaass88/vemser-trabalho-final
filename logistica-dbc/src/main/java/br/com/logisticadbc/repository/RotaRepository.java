package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.Posto;
import br.com.logisticadbc.entity.Rota;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RotaRepository {

    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT LOGISTICA.SEQ_ROTA.NEXTVAL mysequence FROM DUAL";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao buscar sequence de Rotas" + e);
        }
    }

    public Rota adicionar(Rota rota) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(con);
            rota.setIdRota(proximoId);

            //Adicionando a rota na sua respectiva tabela do banco de dados
            String sql = "INSERT INTO LOGISTICA.ROTA\n" +
                    "(ID_ROTA, DESCRICAO, LOCALPARTIDA, LOCALDESTINO)\n" +
                    "VALUES(?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, rota.getIdRota());
            stmt.setString(2, rota.getDescricao());
            stmt.setString(3, rota.getLocalPartida());
            stmt.setString(4, rota.getLocalDestino());

            int res = stmt.executeUpdate();

            if (res == 0) {
                throw new BancoDeDadosException("Erro ao adicionar rota");
            } else {
//                System.out.println("Rota adicionada com sucesso!" +
//                        "\nadicionarRota.res=" + res);
                System.out.println("Rota adicionada com sucesso!");
            }
            return rota;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao adicionar rota" + e);
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

    // adicionarPosto_X_Rota conforme relacionamento na tabela POSTO_X_ROTA
    public Rota adicionarPostoXRota(Rota rota, int index) throws BancoDeDadosException {

        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            //inserindo o id de rota e o id de posto na tabela ROTA_X_POSTO
            String sql = "INSERT INTO LOGISTICA.ROTA_X_POSTO\n" +
                    "(ID_ROTA, ID_POSTO)\n" +
                    "VALUES(?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, rota.getIdRota());
            stmt.setInt(2, rota.getListaPostoCadastrado().get(index).getIdPosto());

            int res = stmt.executeUpdate();

            if (res == 0) {
                throw new BancoDeDadosException("Erro ao adicionar POSTO_X_ROTA");
            } else {
//                System.out.println("POSTO_X_ROTA adicionada com sucesso!" +
//                        "\nadicionarPOSTO_X_ROTA.res=" + res);
                System.out.println("POSTO_X_ROTA adicionada com sucesso!");
            }

            return rota;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao adicionar POSTO_X_ROTA" + e);
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

    // removerPosto_X_Rota conforme relacionamento na tabela POSTO_X_ROTA
    public boolean removerPostoXRota(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM LOGISTICA.ROTA_X_POSTO WHERE ID_ROTA = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            if (res == 0) {
                throw new BancoDeDadosException("Erro ao remover rota");
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

            String sql = "DELETE FROM LOGISTICA.ROTA WHERE ID_ROTA = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            if (res == 0) {
                throw new BancoDeDadosException("Erro ao remover rota");
            } else {
                System.out.println("Rota removida com sucesso!" +
                        "\nremoverRotaPorId.res=" + res);
                return res > 0;
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao remover rota" + e);
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

    public boolean editar(Integer id, Rota rota) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE LOGISTICA.ROTA SET ");
            sql.append("DESCRICAO = ?, ");
            sql.append("LOCALPARTIDA = ?, ");
            sql.append("LOCALDESTINO = ? ");
            sql.append("WHERE ID_ROTA =  ?");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, rota.getDescricao());
            stmt.setString(2, rota.getLocalPartida());
            stmt.setString(3, rota.getLocalDestino());
            stmt.setInt(4, id);

            // Executa-se a consulta
            int res = stmt.executeUpdate();

            if (res == 0) {
                throw new BancoDeDadosException("Erro ao editar rota.");
            } else {
//                System.out.println("Rota editada com sucesso!" +
//                        "\neditarRotaPorId.res=" + res);
                System.out.println("Rota editada com sucesso!");
                return res > 0;
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao editar rota: " + e);
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

    public List<Rota> listar() throws BancoDeDadosException {
        List<Rota> rotas = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "  SELECT r.ID_ROTA, r.DESCRICAO, r.LOCALPARTIDA, r.LOCALDESTINO,  p.ID_POSTO, p.NOMEPOSTO, p.VALORCOMBUSTIVEL  \n" +
                    "\tFROM LOGISTICA.ROTA r\n" +
                    "\t\tINNER JOIN LOGISTICA.ROTA_X_POSTO rxp ON r.ID_ROTA  = rxp.ID_ROTA  \n" +
                    "\t\tINNER JOIN LOGISTICA.POSTO p ON p.ID_POSTO = rxp.ID_POSTO \n" +
                    "\t\tORDER BY r.ID_ROTA \n";


            PreparedStatement stmt = con.prepareStatement(sql);
            // Executa-se a consulta
            ResultSet rs = stmt.executeQuery();

            Rota rotaAnt = new Rota();
            rotaAnt.setIdRota(0);
            int cont = -1;

            while (rs.next()) {
                Rota rota = new Rota();
                Posto posto = new Posto();

                rota.setIdRota(rs.getInt("ID_ROTA"));
                rota.setDescricao(rs.getString("DESCRICAO"));
                rota.setLocalDestino(rs.getString("LOCALPARTIDA"));
                rota.setLocalPartida(rs.getString("LOCALDESTINO"));

                posto.setIdPosto(rs.getInt("ID_POSTO"));
                posto.setNomePosto(rs.getString("NOMEPOSTO"));
                posto.setValorCombustível(rs.getDouble("VALORCOMBUSTIVEL"));
                posto.setIdRota(rs.getInt("ID_ROTA"));

                if (rotaAnt.getIdRota() != rota.getIdRota()) { //Faz com que não se crie rotas repetidas
                    rotas.add(rota);
                    rotaAnt.setIdRota(rota.getIdRota());
                    cont++;
                }

                if (posto.getIdRota() == rota.getIdRota()) {
                    rotas.get(cont).setListaPostoCadastradoPosto(posto);
                }

            }

        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao listar rotas" + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rotas;
    }

    public Rota buscaPorId(int index) throws BancoDeDadosException{
        Connection con = null;
        List<Rota> rotas = new ArrayList<>();
        Rota rota = new Rota();
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "  SELECT r.ID_ROTA, r.DESCRICAO, r.LOCALPARTIDA, r.LOCALDESTINO,  p.ID_POSTO, p.NOMEPOSTO, p.VALORCOMBUSTIVEL  \n" +
                    "\tFROM LOGISTICA.ROTA r\n" +
                    "\t\tINNER JOIN LOGISTICA.ROTA_X_POSTO rxp ON r.ID_ROTA  = rxp.ID_ROTA  \n" +
                    "\t\tINNER JOIN LOGISTICA.POSTO p ON p.ID_POSTO = rxp.ID_POSTO \n" +
                    "\t\tORDER BY r.ID_ROTA \n";

            PreparedStatement stmt = con.prepareStatement(sql);
            // Executa-se a consulta
            ResultSet rs = stmt.executeQuery();

            Rota rotaAnt = new Rota();
            rotaAnt.setIdRota(0);
            int cont = -1;

            while (rs.next()) {
                Posto posto = new Posto();

                rota.setIdRota(rs.getInt("ID_ROTA"));
                rota.setDescricao(rs.getString("DESCRICAO"));
                rota.setLocalDestino(rs.getString("LOCALPARTIDA"));
                rota.setLocalPartida(rs.getString("LOCALDESTINO"));

                posto.setIdPosto(rs.getInt("ID_POSTO"));
                posto.setNomePosto(rs.getString("NOMEPOSTO"));
                posto.setValorCombustível(rs.getDouble("VALORCOMBUSTIVEL"));
                posto.setIdRota(rs.getInt("ID_ROTA"));

                if (rotaAnt.getIdRota() != rota.getIdRota()) { //Faz com que não se crie rotas repetidas
                    rotas.add(rota);
                    rotaAnt.setIdRota(rota.getIdRota());
                    cont++;
                }

                if (posto.getIdRota() == rota.getIdRota()) {
                    rotas.get(cont).setListaPostoCadastradoPosto(posto);
                }

            }

        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao listar rotas" + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rotas.get(0);
    }
}
