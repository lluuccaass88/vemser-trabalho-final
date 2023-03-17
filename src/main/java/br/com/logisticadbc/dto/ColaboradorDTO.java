package br.com.logisticadbc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ColaboradorDTO extends ColaboradorCreateDTO {
    @Schema(description = "Id de usuario", example = "5", required = true)
    private Integer idColaborador;
}