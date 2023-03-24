package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha);

    Optional<UsuarioEntity> findByLogin(String username);

    Optional<UsuarioEntity> findByStatusEqualsIgnoreCase(StatusGeral status);

}