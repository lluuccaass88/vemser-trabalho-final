package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.mongodb.LogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<LogEntity, String> { }