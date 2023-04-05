package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.entity.enums.TipoOperacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogDTO {

    private String id;

    private String loginOperador;

    private String descricao;

    private LocalDateTime data;

    private TipoOperacao tipoOperacao;
}