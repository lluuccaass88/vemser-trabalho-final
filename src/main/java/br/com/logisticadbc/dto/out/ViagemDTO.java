package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.ViagemCreateDTO;
import br.com.logisticadbc.entity.enums.StatusViagem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ViagemDTO extends ViagemCreateDTO {

    @Schema(description = "ID da viagem", example = "1")
    private Integer idViagem;

    @Schema(description = "Status da viagem", example = "FINALIZADA")
    private StatusViagem statusViagem;

    @Schema(description = "Id do Usuário", example = "1")
    private Integer idUsuario;

}


