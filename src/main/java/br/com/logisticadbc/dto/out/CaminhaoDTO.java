package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.CaminhaoCreateDTO;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CaminhaoDTO extends CaminhaoCreateDTO {

    @Schema(description = "Id do Caminhão", example = "1")
    private Integer idCaminhao;

    @Schema(description = "Se o caminhao já está viajando", example = "ESTACIONADO")
    private StatusCaminhao statusCaminhao;
}
