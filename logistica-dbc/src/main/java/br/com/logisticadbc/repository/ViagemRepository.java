package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.Caminhao;
import br.com.logisticadbc.entity.Viagem;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ViagemRepository {

    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT LOGISTICA.SEQ_VIAGEM.NEXTVAL mysequence FROM DUAL";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao buscar sequence de viagem " + e);
        }
    }


    public Viagem adicionar(Viagem viagem) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(con);
            viagem.setIdViagem(proximoId);

            String sql = "INSERT INTO LOGISTICA.VIAGEM\n" +
                    "(ID_VIAGEM, ID_CAMINHAO, ID_ROTA, ID_USUARIO, FINALIZADA)\n" +
                    "VALUES(?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, viagem.getIdViagem());
            stmt.setInt(2, viagem.getCaminhao().getIdCaminhao());
            stmt.setInt(3, viagem.getRota().getIdRota());
            stmt.setInt(4, viagem.getUsuario().getId());
//            stmt.setInt(5, viagem.se);

            int res = stmt.executeUpdate();
            if (res == 0) {
                throw new BancoDeDadosException("Erro ao adicionar viagem");
            } else {
                System.out.println("Viagem cadastrada com sucesso!" +
                        "\nadicionarViagem.res=" + res);
            }
            return viagem;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao adicionar viagem: " + e);
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
