package br.com.logisticadbc.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class CargosDeUsuarioDTO {

    @Schema(description = "Usuário")
    private UsuarioDTO usuario;

    @Schema(description = "Cargos do usuário")
    private Set<CargoDTO> cargos;
}
