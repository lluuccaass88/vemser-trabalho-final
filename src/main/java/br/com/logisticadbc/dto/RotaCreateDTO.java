package br.com.logisticadbc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RotaCreateDTO {

    @NotNull
    @NotBlank
    @Schema(description = "Descrição da Rota", example = "Rota de São Paulo até Brasilia", required = true)
    private String descricao;
    @NotNull
    @NotBlank
    @Schema(description = "Destino Inicial da Rota", example = "São Paulo", required = true)
    private String localPartida;

    @NotNull
    @NotBlank
    @Schema(description = "Destino Final da Rota", example = "Brasilia", required = true)
    private String localDestino;

}