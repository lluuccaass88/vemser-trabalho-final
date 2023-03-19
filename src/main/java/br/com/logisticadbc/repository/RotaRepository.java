package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RotaRepository extends JpaRepository<RotaEntity, Integer> {
    List<RotaEntity> findBylocalPartida(String localPartida);
    List<RotaEntity> findBylocalDestino(String localDestino);
    List<RotaEntity> findByStatusEquals(StatusGeral status);

}