package br.com.logisticadbc.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostoCreateDTO {

    @NotNull
    @NotBlank
    @Schema(description = "Nome do posto", example = "Posto Ipiranga")
    private String nome;

    @NotNull
    @NotBlank
    @Schema(description = "Longitude (-180 a 180)", example = "-38.5434")
    private String longitude;

    @NotNull
    @NotBlank
    @Schema(description = "Latitude (-90 a 90)", example = "-3.71839")
    private String latitude;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[^0-9]*", message = "Deve conter apenas números")
    @Pattern(regexp = "[a-zA-Z]*", message = "Não deve conter letras")
    @Schema(description = "Cidade em que está o posto", example = "Fortaleza")
    private String cidade;

    @NotNull
    @Schema(description = "Valor do combustivel no posto", example = "5.89")
    private Double valorCombustivel;
}
