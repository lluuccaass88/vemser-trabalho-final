package br.com.logisticadbc.entity.mongodb;

import br.com.logisticadbc.entity.enums.TipoOperacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "logs")
public class LogEntity {

    @Id
    private String id;

    private String loginOperador;

    private String descricao;

    private TipoOperacao tipoOperacao;

}