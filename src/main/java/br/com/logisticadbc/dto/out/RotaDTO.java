package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.RotaCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RotaDTO extends RotaCreateDTO {

    @Schema(description = "ID da Rota")
    private Integer idRota;

    @Schema(description = "ID do Usuário")
    private Integer idUsuario;
}