package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.CargoEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha);

    Optional<UsuarioEntity> findByLogin(String username);

    Optional<UsuarioEntity> findByStatusEquals(StatusGeral status);

//    Set<UsuarioEntity> findByCargosEquals(Set<CargoEntity> cargos);
    /*
    * SE BASEAR NISSO
    * SELECT * FROM USUARIO u
	JOIN CARGO_X_USUARIO cxu ON u.ID_USUARIO = cxu.ID_USUARIO
		WHERE cxu.ID_CARGO  = 2
    * */

}