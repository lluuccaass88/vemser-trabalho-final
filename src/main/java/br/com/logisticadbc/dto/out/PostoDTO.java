package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.PostoCreateDTO;
import br.com.logisticadbc.entity.enums.StatusGeral;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PostoDTO extends PostoCreateDTO {

    @Schema(description = "ID do Colaborador", example = "1")
    private Integer idUsuario;

    @Schema(description = "id do posto", example = "1")
    private Integer idPosto;

    @Schema(description = "Se o posto está ativo", example = "ATIVO")
    private StatusGeral status;
}
