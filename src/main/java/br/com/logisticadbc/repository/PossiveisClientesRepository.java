package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.mongodb.PossiveisClientesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PossiveisClientesRepository extends MongoRepository<PossiveisClientesEntity, String> {

    List<PossiveisClientesEntity> findByData(LocalDate data);
    Optional<PossiveisClientesEntity> findByEmail(String email);
}
