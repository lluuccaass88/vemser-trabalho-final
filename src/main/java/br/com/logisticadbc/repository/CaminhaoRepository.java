package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaminhaoRepository extends JpaRepository<CaminhaoEntity, Integer> {

    List<CaminhaoEntity> findByStatusCaminhaoEquals (StatusCaminhao statusCaminhao);
}