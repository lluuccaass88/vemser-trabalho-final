package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.mongodb.PostoEntity;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostoRepository extends MongoRepository<PostoEntity, String> {

    List<PostoEntity> findByStatusEquals(StatusGeral status);

    List<PostoEntity> findByLocationNear(Point point, Distance distance);

    List<PostoEntity> findByCidadeIgnoreCase(String cidade);

}