package br.com.logisticadbc.entity.mongodb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "possiveis-clientes")
public class PossiveisClientesEntity {

    @Id
    private String id;

    private String email;

    private String nome;

    private LocalDate data;
}
