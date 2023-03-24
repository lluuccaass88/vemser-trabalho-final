package br.com.logisticadbc.repository;

import br.com.logisticadbc.dto.out.UsuarioCompletoDTO;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.StatusViagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha);

    Optional<UsuarioEntity> findByLogin(String username);
    Optional<UsuarioEntity> findById(Integer id);

    Optional<UsuarioEntity> findByStatusEquals(StatusGeral status);

//    @Query("SELECT u FROM UsuarioEntity u JOIN u.cargos c WHERE c.id = :id")
//    Set<UsuarioEntity> findByCargoUsuario(Integer id);

    @Query("  SELECT u " +
            "  from USUARIO u" +
            "  JOIN u.cargos c" +
            "  WHERE  c.nome = :cargo"
    )
    Page<UsuarioEntity> findByCargoUsuario(Pageable pageable, String cargo);

//    Set<UsuarioEntity>;
    /*
    * SE BASEAR NISSO
    * SELECT * FROM USUARIO u
	JOIN CARGO_X_USUARIO cxu ON u.ID_USUARIO = cxu.ID_USUARIO
		WHERE cxu.ID_CARGO  = 2
    * */

    @Query(" SELECT u " +
            "  FROM USUARIO u " +
            "  JOIN u.cargos c " +
            " WHERE c.nome = :cargo AND u.status = :status"
    )
    Page<UsuarioEntity> findByCargosAndStatus(Pageable pageable, String cargo, StatusGeral status);

    @Query(" SELECT DISTINCT new br.com.logisticadbc.dto.out.UsuarioCompletoDTO(" +
            "   u.idUsuario, " +
            "   u.login, " +
            "   u.nome, " +
            "   u.email, " +
            "   u.documento, " +
            "   u.status, " +

            "   ca.idCargo, " +
            "   ca.nome, " +

            "   c.idCaminhao, " +
            "   c.modelo, " +
            "   c.placa, " +
            "   c.nivelCombustivel, " +
            "   c.statusCaminhao, " +
            "   c.status, " +

            "   r.idRota, " +
            "   r.descricao, " +
            "   r.localPartida, " +
            "   r.localDestino, " +
            "   r.status, " +

            "   p.idPosto, " +
            "   p.nome, " +
            "   p.valorCombustivel, " +
            "   p.status, " +

            "   v.idViagem, " +
            "   v.descricao, " +
            "   v.dataInicio, " +
            "   v.dataFim, " +
            "   v.statusViagem " +
            ")" +
            "   From USUARIO u " +
            "   left join u.caminhoes c" +
            "   left join u.rotas r" +
            "   left join u.postos p" +
            "   left join u.viagens v" +
            "   left join u.cargos ca" +
            "   ORDER BY ca.nome"
    )
    Page<UsuarioCompletoDTO> relatorio (Pageable pageable);

    @Query(" SELECT u " +
            " FROM USUARIO u " +
            " LEFT JOIN u.viagens v " +
            " LEFT JOIN u.cargos c " +
            " WHERE v.statusViagem = :statusViagem AND u.status = 'ATIVO' OR c.nome = :cargo"
    )
    Page<UsuarioEntity> findByUsuarioLivre(Pageable pageable, StatusViagem statusViagem, String cargo);
}