package br.com.logisticadbc.entity.mongodb;

import br.com.logisticadbc.entity.enums.StatusGeral;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "postos")
public class PostoEntity {

    @Id
    private String id;

    private String nome;

    // LONGITUDE | LATITUDE
    private GeoJsonPoint location;

    private String cidade;

    private Double valorCombustivel;

    @Enumerated(EnumType.STRING)
    private StatusGeral status; // 0 - INATIVO | 1 - ATIVO

}