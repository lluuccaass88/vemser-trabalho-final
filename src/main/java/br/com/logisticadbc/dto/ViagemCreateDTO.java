package br.com.logisticadbc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ViagemCreateDTO {
    @NotNull
    @Schema(description = "Id do caminhão vinculado com a viagem", example = "1", required = true)
    private Integer idCaminhao;
    @NotNull
    @Schema(description = "Id da rota vinculada com a viagem", example = "1", required = true)
    private Integer idRota;
    @NotNull
    @Schema(description = "Id do motorista vinculado com a viagem", example = "1", required = true)
    private Integer idMotorista;
}
