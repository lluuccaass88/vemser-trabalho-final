package br.com.logisticadbc.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CaminhaoCreateDTO {
    @NotNull
    @NotBlank
    @Schema(description = "Modelo do Caminhão", example = "Mercedes")
    private String modelo;

    @NotNull
    @NotBlank
    @Schema(description = "Placa do caminhao ( UNICA )", example = "ABC1234")
    private String placa;

    @NotNull
    @Schema(description = "Quantidade de Gasolina no Caminhão", example = "20")
    private Integer nivelCombustivel;

}
