package br.com.logisticadbc.entity.mongodb;

import br.com.logisticadbc.entity.enums.TipoOperacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

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

    private LocalDateTime data;
    @Enumerated(EnumType.STRING)
    private TipoOperacao tipoOperacao;

}