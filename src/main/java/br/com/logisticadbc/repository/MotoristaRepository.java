package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.MotoristaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoristaRepository extends JpaRepository<MotoristaEntity, Integer> { }