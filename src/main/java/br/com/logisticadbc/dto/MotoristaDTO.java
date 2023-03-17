package br.com.logisticadbc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MotoristaDTO extends MotoristaCreateDTO {

    @Schema(description = "Id do Usuário", example = "1")
    private Integer idUsuario;
    @Schema(description = "Status do Usuário", example = "0 - ATIVO ou 1 - INATIVO")
    private Integer statusUsuario;

    @Schema(description = "Status do Motorista", example = "0 - DISPONIVEL ou 1 - EM VIAGEM")
    private Integer statusMotorista;
}