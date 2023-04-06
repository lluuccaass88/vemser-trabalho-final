package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.mongodb.LogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<LogEntity, String> {
    List<LogEntity> findByData(String dataBuscada);



}