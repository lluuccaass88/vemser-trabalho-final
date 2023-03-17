package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.ColaboradorCreateDTO;
import br.com.logisticadbc.entity.enums.StatusUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ColaboradorDTO extends ColaboradorCreateDTO {

    @Schema(description = "Id de usuario")
    private Integer idUsuario;

    @Schema(description = "Status do usuário")
    private StatusUsuario statusUsuario;
}