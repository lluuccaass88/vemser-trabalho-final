package br.com.logisticadbc.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RotaCreateDTO {

    @NotNull
    @NotBlank
    @Schema(description = "Descrição da Rota", example = "Rota de São Paulo até Brasilia")
    private String descricao;

    @NotNull
    @NotBlank
    @Schema(description = "Destino Inicial da Rota", example = "São Paulo")
    private String localPartida;

    @NotNull
    @NotBlank
    @Schema(description = "Destino Final da Rota", example = "Brasilia")
    private String localDestino;

}