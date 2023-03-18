package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.ColaboradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboradorRepository extends JpaRepository<ColaboradorEntity, Integer> {}