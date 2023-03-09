package br.com.logisticadbc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PostoCreateDTO {
//    @NotNull
//    private int idRota;
    @NotBlank
    @Schema(description = "Nome do posto", example = "Posto Ipiranga", required = true)
    private String nomePosto;
    @NotNull
    @Schema(description = "Valor do combustivel no posto", example = "5,89", required = true)
    private double valorCombustível;

}
