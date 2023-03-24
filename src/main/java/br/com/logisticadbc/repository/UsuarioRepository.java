package br.com.logisticadbc.repository;

import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.CargoEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha);

    Optional<UsuarioEntity> findByLogin(String username);

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
}