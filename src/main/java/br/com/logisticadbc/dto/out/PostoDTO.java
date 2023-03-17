package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.PostoCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PostoDTO extends PostoCreateDTO {

    @Schema(description = "id do posto", example = "1", required = true)
    private Integer idPosto;
}
