package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.MotoristaCreateDTO;
import br.com.logisticadbc.entity.enums.StatusMotorista;
import br.com.logisticadbc.entity.enums.StatusUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MotoristaDTO extends MotoristaCreateDTO {

    @Schema(description = "Id do Usuário", example = "1")
    private Integer idUsuario;

    @Schema(description = "Status do Usuário", example = "ATIVO")
    private StatusUsuario statusUsuario;

    @Schema(description = "Status do Motorista", example = "DISPONIVEL")
    private StatusMotorista statusMotorista;
}