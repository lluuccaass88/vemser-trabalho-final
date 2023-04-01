package br.com.logisticadbc.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @Min(1)
    @Max(100)
    @Schema(description = "Porcentagem de Gasolina no tanque (%)", example = "20")
    private Integer nivelCombustivel;

}
