package br.com.logisticadbc.dto.in;

import br.com.logisticadbc.entity.enums.StatusCaminhao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CaminhaoCreateDTO {
    @NotBlank
    @Schema(description = "Modelo do Caminhão", example = "Mercedes", required = true)
    private String modelo;
    @NotBlank
    @Schema(description = "Placa do caminhao ( UNICA )", example = "ABC1234", required = true)
    private String placa;
    @NotNull
    @Schema(description = "Quantidade de Gasolina no Caminhão", example = "20", required = true)
    private Integer nivelCombustivel;

}