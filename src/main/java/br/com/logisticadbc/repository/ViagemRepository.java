package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.Viagem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Integer> {}
//
//    private final ConexaoBancoDeDados conexaoBancoDeDados;
//
//    public Integer getProximoId(Connection connection) throws SQLException {
//        try {
//            String sql = "SELECT LOGISTICA.SEQ_VIAGEM.NEXTVAL mysequence FROM DUAL";
//
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//
//            if (rs.next()) {
//                return rs.getInt("mysequence");
//            }
//            return null;
//        } catch (SQLException e) {
//            throw new BancoDeDadosException("Erro ao buscar sequence de viagem " + e);
//        }
//    }
//
//    public Viagem adicionar(Viagem viagem) throws BancoDeDadosException {
//        Connection con = null;
//        try {
//            con = conexaoBancoDeDados.getConnection();
//            Integer proximoId = this.getProximoId(con);
//            viagem.setIdViagem(proximoId);
//
//            String sql = "INSERT INTO LOGISTICA.VIAGEM\n" +
//                    "(ID_VIAGEM, ID_CAMINHAO, ID_ROTA, ID_USUARIO, FINALIZADA)\n" +
//                    "VALUES(?, ?, ?, ?, ?)";
//
//            PreparedStatement stmt = con.prepareStatement(sql);
//
//            stmt.setInt(1, viagem.getIdViagem());
//            stmt.setInt(2, viagem.getIdCaminhao());
//            stmt.setInt(3, viagem.getIdRota());
//            stmt.setInt(4, viagem.getIdUsuario());
//            if(viagem.isFinalizada()){
//                stmt.setInt(5, 1);
//            }else{
//                stmt.setInt(5, 0);
//            }
//
//            int res = stmt.executeUpdate();
//            if (res > 0) {
//                return viagem;
//            } else {
//                throw new BancoDeDadosException("Erro ao adicionar viagem");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new BancoDeDadosException("Erro ao adicionar viagem: ");
//        } finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public List<Viagem> listar() throws BancoDeDadosException {
//        List<Viagem> viagens = new ArrayList<>();
//        Connection con = null;
//        try {
//            con = conexaoBancoDeDados.getConnection();
//
//            String sql = "SELECT * \n" +
//                    "\tFROM LOGISTICA.VIAGEM v\n" +
//                    "\t\tINNER JOIN LOGISTICA.USUARIO u  ON v.ID_USUARIO = u.ID_USUARIO \n" +
//                    "\t\tINNER JOIN LOGISTICA.CAMINHAO c ON c.ID_CAMINHAO = v.ID_CAMINHAO \n" +
//                    "\t\tINNER JOIN LOGISTICA.ROTA r ON r.ID_ROTA = r.ID_ROTA \n" +
//                    "ORDER BY v.ID_VIAGEM";
//
//            PreparedStatement stmt = con.prepareStatement(sql);
//            // Executa-se a consulta
//            ResultSet rs = stmt.executeQuery();
//
//            Viagem viagemAnt = new Viagem();
//            viagemAnt.setIdViagem(0);
//
//            while (rs.next()) {
//                Viagem viagem = new Viagem();
//                Caminhao caminhao = new Caminhao();
//
//                ViagemUsuarioDTO viagemUsuarioDTO = new ViagemUsuarioDTO();
//                ViagemRotaDTO viagemRotaDTO = new ViagemRotaDTO();
//
//                viagemUsuarioDTO.setNomeUsuario(rs.getString("NOME"));
//                viagemUsuarioDTO.setIdUsuario(rs.getInt("ID_USUARIO"));
//                viagemUsuarioDTO.setTipoUsuario(Perfil.ofTipoPerfil(rs.getInt("PERFIL")));
//
//                viagemRotaDTO.setIdRota(rs.getInt("ID_ROTA"));
//                viagemRotaDTO.setLocalDestino(rs.getString("LOCALDESTINO"));
//                viagemRotaDTO.setLocalPartida(rs.getString("LOCALPARTIDA"));
//
//                caminhao.setIdCaminhao(rs.getInt("ID_CAMINHAO"));
//                caminhao.setModelo(rs.getString("MODELO"));
//                caminhao.setPlaca(rs.getString("PLACA"));
//                caminhao.setGasolina(rs.getInt("GASOLINA"));
//                caminhao.setEmViagem(EmViagem.getOpcaoEmViagem(rs.getInt("EMVIAGEM")));
//
//                viagem.setIdViagem(rs.getInt("ID_VIAGEM"));
//
//                if(rs.getInt("FINALIZADA") == 1){
//                    viagem.setFinalizada(true);
//                }else{
//                    viagem.setFinalizada(false);
//                }
//                viagem.setCaminhao(caminhao);
//                viagem.setUsuario(viagemUsuarioDTO);
//                viagem.setRota(viagemRotaDTO);
//
//                if (viagemAnt.getIdViagem() != viagem.getIdViagem()) { //Faz com que não se crie rotas repetidas
//                    viagens.add(viagem);
//                    viagemAnt.setIdViagem(viagem.getIdViagem());
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new BancoDeDadosException("Falha ao listar viagens cadastradas");
//        } finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return viagens;
//    }
//
//    public Viagem finalizarViagem(Integer id, Viagem viagem) throws BancoDeDadosException {
//        Connection con = null;
//        try {
//            con = conexaoBancoDeDados.getConnection();
//            StringBuilder sql = new StringBuilder();
//            sql.append("UPDATE LOGISTICA.VIAGEM SET\n");
//            sql.append(" ID_CAMINHAO = ?,");
//            sql.append(" ID_ROTA = ?,");
//            sql.append(" ID_USUARIO = ?,");
//            sql.append(" FINALIZADA = ?");
//            sql.append(" WHERE ID_VIAGEM = ?");
//
//            PreparedStatement stmt = con.prepareStatement(sql.toString());
//
//            stmt.setInt(1, viagem.getCaminhao().getIdCaminhao());
//            stmt.setInt(2, viagem.getRota().getIdRota());
//            stmt.setInt(3, viagem.getUsuario().getIdUsuario());
//
//            if(viagem.isFinalizada()){
//                stmt.setInt(4, 1);
//            }else{
//                stmt.setInt(4, 0);
//            }
//            stmt.setInt(5, id);
//            int res = stmt.executeUpdate();
//
//            if (res == 0) {
//                throw new BancoDeDadosException("Erro ao editar viagem");
//            } else {
//                log.info("Viagem editada com sucesso!" +
//                        "\neditarViagem.res=" + res);
//                return viagem;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new BancoDeDadosException("Falha ao tentar implementar o metodo finalizar viagem");
//        } finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public Viagem editar(Integer id, Viagem viagem) throws BancoDeDadosException {
//        Connection con = null;
//        try {
//            con = conexaoBancoDeDados.getConnection();
//            StringBuilder sql = new StringBuilder();
//            sql.append("UPDATE LOGISTICA.VIAGEM SET\n");
//            sql.append(" ID_CAMINHAO = ?,");
//            sql.append(" ID_ROTA = ?,");
//            sql.append(" ID_USUARIO = ?,");
//            sql.append(" FINALIZADA = ?");
//            sql.append(" WHERE ID_VIAGEM = ?");
//
//            PreparedStatement stmt = con.prepareStatement(sql.toString());
//
//            stmt.setInt(1, viagem.getIdCaminhao());
//            stmt.setInt(2, viagem.getIdRota());
//            stmt.setInt(3, viagem.getIdUsuario());
//            if(viagem.isFinalizada()){
//                stmt.setInt(4, 1);
//            }else{
//                stmt.setInt(4, 0);
//            }
//            stmt.setInt(5, id);
//            int res = stmt.executeUpdate();
//
//            if (res == 0) {
//                throw new BancoDeDadosException("Erro ao editar viagem");
//            } else {
//                log.info("Viagem editada com sucesso!" +
//                        "\neditarViagem.res=" + res);
//                return viagem;
//            }
//        } catch (SQLException e) {
//            throw new BancoDeDadosException("Erro ao editar viagem" + e);
//        } finally {
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
