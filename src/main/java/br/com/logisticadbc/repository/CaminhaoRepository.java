package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.Caminhao;
import br.com.logisticadbc.entity.EmViagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class CaminhaoRepository{

    private final ConexaoBancoDeDados conexaoBancoDeDados;
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
            con = conexaoBancoDeDados.getConnection();
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
            if (res > 0) {
                return caminhao;
            } else {
                throw new BancoDeDadosException("Erro ");
            }
        } catch(SQLIntegrityConstraintViolationException e){
            throw new BancoDeDadosException("A placa do caminhão não pode ser igual a uma placa já cadastrada no sistema. ");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Erro ao adicionar caminhão: ");
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

            String sql = "DELETE FROM CAMINHAO WHERE ID_CAMINHAO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            // Executa-se a consulta SQL
            int res = stmt.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Erro ao remover caminhão");
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


    public boolean editar(Integer id, Caminhao caminhao) throws BancoDeDadosException {
        Connection con = null;
        System.out.println(caminhao);
        try {
            con = conexaoBancoDeDados.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CAMINHAO SET ");
            sql.append("MODELO = ?, ");
            sql.append("PLACA = ?, ");
            sql.append("GASOLINA = ?, ");
            sql.append("EMVIAGEM = ? ");
            sql.append("WHERE ID_CAMINHAO = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, caminhao.getModelo());
            stmt.setString(2, caminhao.getPlaca());
            stmt.setInt(3, caminhao.getGasolina());
            stmt.setInt(4, caminhao.getEmViagem().getOpcao());
            stmt.setInt(5, id);


            // Executa-se a consulta
            int res = stmt.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Erro ao editar caminhão");
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

            con = conexaoBancoDeDados.getConnection();

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
            e.printStackTrace();
            throw new BancoDeDadosException("Erro ao listar caminhoes cadastrados");
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
            con = conexaoBancoDeDados.getConnection();

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
            e.printStackTrace();
            throw new BancoDeDadosException("Erro ao buscar caminhoes cadastrados por ID");
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
            con = conexaoBancoDeDados.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CAMINHAO SET ");
            sql.append("EMVIAGEM = 1 ");
            sql.append("WHERE ID_CAMINHAO = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, index);
            // Executa-se a consulta
            int res = stmt.executeUpdate();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Falha ao estacionar caminhão");
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

    public boolean viajar(int index) throws BancoDeDadosException {
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CAMINHAO SET ");
            sql.append("EMVIAGEM = 2 ");
            sql.append("WHERE ID_CAMINHAO = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, index);
            // Executa-se a consulta
            int res = stmt.executeUpdate();
            System.out.println("EmVoiagemEditado.res=" + res);

            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Falha ao tentar implementar viagem com caminhao");
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

    public Caminhao abastecerCaminhao(int index, int gasolina) throws BancoDeDadosException {
        Connection con = null;
        Caminhao caminhao = new Caminhao();

        try {
            con = conexaoBancoDeDados.getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CAMINHAO SET ");
            sql.append("GASOLINA = ? ");
            sql.append("WHERE ID_CAMINHAO = ?");
            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setInt(1, gasolina);
            stmt.setInt(2, index);

            // Executa-se a consulta
            int res = stmt.executeUpdate();
            if (res > 0) {
                return caminhao;
            } else {
                throw new BancoDeDadosException("Erro ao abastecer caminhão");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Falha ao implementar método para abastecer caminhão");
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