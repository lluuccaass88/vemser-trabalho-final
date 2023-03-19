package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.RotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RotaRepository extends JpaRepository<RotaEntity, Integer> {



}