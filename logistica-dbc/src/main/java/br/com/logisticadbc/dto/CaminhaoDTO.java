package br.com.logisticadbc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CaminhaoDTO extends CaminhaoCreateDTO{
    @NotNull
    @Schema(description = "Id do Caminhão", example = "1", required = true)
    private Integer idCaminhao;
}
