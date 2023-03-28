package br.com.logisticadbc.repository;

import br.com.logisticadbc.entity.mongodb.PostoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostoDocumentRepository extends MongoRepository<PostoDocument, String> {

    List<PostoDocument> findByCidade(String cidade);
}
