package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RotaRepository extends JpaRepository<RotaEntity, Integer> {

    List<RotaEntity> findBylocalPartidaIgnoreCase(String localPartida);
    List<RotaEntity> findBylocalDestinoIgnoreCase(String localDestino);
    List<RotaEntity> findByStatusEquals(StatusGeral status);

}