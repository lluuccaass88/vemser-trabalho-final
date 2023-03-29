package br.com.logisticadbc.entity.mongodb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document(collection = "posto")
public class PostoDocument {

    @Id
    private String id;

    private String cidade;
}
