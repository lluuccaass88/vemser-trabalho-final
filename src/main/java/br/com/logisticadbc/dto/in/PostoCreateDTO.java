package br.com.logisticadbc.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PostoCreateDTO {

    @NotBlank
    @Schema(description = "Nome do posto", example = "Posto Ipiranga")
    private String nome;
    @NotNull
    @Schema(description = "Valor do combustivel no posto", example = "5.89")
    private BigDecimal valorCombustivel;
}