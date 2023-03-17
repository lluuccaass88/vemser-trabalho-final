package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.enums.StatusCaminhao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CaminhaoDTO extends CaminhaoCreateDTO{

    @Schema(description = "Id do Caminhão", example = "1", required = true)
    private Integer idCaminhao;

    @Schema(description = "Se o caminhao já está viajando", example = "ESTACIONADO ou EM_VIAGEM", required = true)
    private StatusCaminhao statusCaminhao; // 1 - estacionado | 2 - em viagem
}
