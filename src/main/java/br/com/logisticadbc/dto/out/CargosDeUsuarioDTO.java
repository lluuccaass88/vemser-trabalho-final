package br.com.logisticadbc.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class CargosDeUsuarioDTO {

    @Schema(description = "Usuário")
    private UsuarioDTO usuarioDTO;

    @Schema(description = "Cargos do usuário")
    private Set<CargoDTO> cargoDTOS;
}
