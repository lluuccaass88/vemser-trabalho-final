package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {

    Optional<CargoEntity> findByNome(String nome);
}
