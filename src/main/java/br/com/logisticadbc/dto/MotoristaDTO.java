package br.com.logisticadbc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MotoristaDTO extends MotoristaCreateDTO {

    @Schema(description = "Id do Motorista", example = "1")
    private Integer idUsuario;
    @Schema(description = "Status do Motorista", example = "0 - ATIVO ou 1 - INATIVO")
    private Integer statusUsuario;
}