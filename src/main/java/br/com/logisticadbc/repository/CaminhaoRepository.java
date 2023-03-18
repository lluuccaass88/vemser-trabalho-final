package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.CaminhaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaminhaoRepository extends JpaRepository<CaminhaoEntity, Integer> {

//    List<CaminhaoEntity> findBy
}