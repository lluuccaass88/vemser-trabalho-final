package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Perfil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ViagemUsuarioDTO {
    @Schema(description = "id do usuario", example = "1", required = true)
    private int idUsuario;
    @Schema(description = "Nome do usuario", example = "Lucas Alves", required = true)
    private String nomeUsuario;
    @Schema(description = "Tido de usuario", example = "Motorista ou Colaborador", required = true)
    private Perfil tipoUsuario;
}
