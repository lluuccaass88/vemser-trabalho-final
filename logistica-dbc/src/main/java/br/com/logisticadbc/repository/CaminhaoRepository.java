package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.Caminhao;
import br.com.logisticadbc.entity.EmViagem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CaminhaoRepository{
    
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT SEQ_CAMINHAO.NEXTVAL mysequence FROM DUAL";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao buscar sequence de Caminhão " + e);
        }
    }


    public Caminhao adicionar(Caminhao caminhao) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(con);
            caminhao.setIdCaminhao(proximoId);

            String sql = "INSERT INTO CAMINHAO\n" +
                    "(ID_CAMINHAO, MODELO, PLACA, GASOLINA, EMVIAGEM)\n" +
                    "VALUES(?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, caminhao.getIdCaminhao());
            stmt.setString(2, caminhao.getModelo());
            stmt.setString(3, caminhao.getPlaca());
            stmt.setInt(4, caminhao.getGasolina());
            stmt.setInt(5, caminhao.getEmViagem().getOpcao());

            int res = stmt.executeUpdate();
            if (res == 0) {
                throw new BancoDeDadosException("Erro ao adicionar caminhão no banco de dados");
            } else {
                System.out.println("Caminhão cadastrado com sucesso!" +
                        "\nadicionarCaminhão.res=" + res);
            }
            return caminhao;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao adicionar caminhão: " + e);
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

            String sql = "DELETE FROM CAMINHAO WHERE ID_CAMINHAO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            // Executa-se a consulta SQL
            int res = stmt.executeUpdate();
            if (res == 0) {
                throw new BancoDeDadosException("Erro ao remover caminhão");
            } else {
                System.out.println("Caminhão removido com sucesso!" +
                        "\nremoverCaminhão.res=" + res);
                return res > 0;
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao remover caminhão" + e);
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


    public boolean editar(Integer id, Caminhao caminhao) throws BancoDeDadosException {
        Connection con = null;
        System.out.println(caminhao);
        try {
            con = ConexaoBancoDeDados.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CAMINHAO SET ");
            sql.append("MODELO = ?, ");
            sql.append("PLACA = ?, ");
            sql.append("GASOLINA = ? ");
            sql.append("EMVIAGEM = ? ");
            sql.append("WHERE ID_CAMINHAO = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, caminhao.getModelo());
            stmt.setString(2, caminhao.getPlaca());
            stmt.setInt(3, caminhao.getGasolina());
            stmt.setInt(5, caminhao.getEmViagem().getOpcao());
            stmt.setInt(4, id);


            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("editarCaminhao.res=" + res);
            if (res == 0) {
                throw new BancoDeDadosException("Erro ao editar caminhão");
            } else {
//                System.out.println("Caminhão editado com sucesso!" +
//                        "\neditarCaminhão.res=" + res);
                System.out.println("Caminhão editado com sucesso!");
                return res > 0;
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao editar caminhão" + e);
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

    public List<Caminhao> listar() throws BancoDeDadosException {
        List<Caminhao> caminhoes = new ArrayList<>();
        Connection con = null;
        try {

            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM CAMINHAO";

            PreparedStatement stmt = con.prepareStatement(sql);
            // Executa-se a consulta
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Caminhao caminhao = new Caminhao();
                caminhao.setIdCaminhao(rs.getInt("ID_CAMINHAO"));
                caminhao.setModelo(rs.getString("MODELO"));
                caminhao.setPlaca(rs.getString("PLACA"));
                caminhao.setGasolina(rs.getInt("GASOLINA"));
                caminhao.setEmViagem(EmViagem.getOpcaoEmViagem(rs.getInt("EMVIAGEM"))); //O erro deve ser aqui dentro, ele fala que não esta retornando nada
                caminhoes.add(caminhao);
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao listar caminhoes cadastrados: " + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return caminhoes;
    }

    public Caminhao buscaPorId(int index) throws BancoDeDadosException {
        Connection con = null;
        Caminhao caminhao = new Caminhao();
        try {
            con = ConexaoBancoDeDados.getConnection();

//            String sql = "SELECT * FROM CAMINHAO c \n" +
//                    "\tWHERE ID_CAMINHAO = ?";
            String sql = "SELECT * FROM CAMINHAO \n" +
                    "WHERE ID_CAMINHAO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, index);

            // Executa-se a consulta
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                caminhao.setIdCaminhao(rs.getInt("ID_CAMINHAO"));
                caminhao.setModelo(rs.getString("MODELO"));
                caminhao.setPlaca(rs.getString("PLACA"));
                caminhao.setGasolina(rs.getInt("GASOLINA"));
                caminhao.setEmViagem(EmViagem.getOpcaoEmViagem(rs.getInt("EMVIAGEM")));
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao listar caminhoes cadastrados: " + e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return caminhao;
    }

    public boolean estacionar(int index) throws BancoDeDadosException {
        Connection con = null;

        try {
            con = ConexaoBancoDeDados.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CAMINHAO SET ");
            sql.append("EMVIAGEM = 1 ");
            sql.append("WHERE ID_CAMINHAO = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, index);
            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("EmVoiagemEditado.res=" + res);

            if (res == 0) {
                throw new BancoDeDadosException("Erro ao editar caminhão");
            } else {
                System.out.println("Caminhão editado com sucesso!" +
                        "\neditarCaminhão.res=" + res);
                return true;
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao editar caminhão" + e);
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

    public boolean viajar(int index) throws BancoDeDadosException {

        Connection con = null;

        try {
            con = ConexaoBancoDeDados.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CAMINHAO SET ");
            sql.append("EMVIAGEM = 2 ");
            sql.append("WHERE ID_CAMINHAO = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, index);
            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("EmVoiagemEditado.res=" + res);

            if (res == 0) {
                throw new BancoDeDadosException("Erro ao editar caminhão");
            } else {
                System.out.println("Caminhão editado com sucesso!" +
                        "\neditarCaminhão.res=" + res);
                return true;
            }

        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao editar caminhão" + e);
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

    public Caminhao abastecerCaminhao(int index, int gasolina) throws BancoDeDadosException {

        Connection con = null;
        Caminhao caminhao = new Caminhao();
        try {
            con = ConexaoBancoDeDados.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CAMINHAO SET ");
            sql.append("GASOLINA = ? ");
            sql.append("WHERE ID_CAMINHAO = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, gasolina);
            stmt.setInt(2, index);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("abastecerCaminhao.res=" + res);

            if (res == 0) {
                throw new BancoDeDadosException("Erro ao abastecer caminhão");
            } else {
                System.out.println("Caminhão abastecido com sucesso!" +
                        "\nabastecerCaminhao.res=" + res);
                return caminhao;
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao abastecer caminhão" + e);
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