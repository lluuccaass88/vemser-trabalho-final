package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Perfil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ViagemUsuarioDTO {

    @Schema(description = "Id do Usuário que está em Rota", example = "1", required = true)
    private int idUsuario;
    @Schema(description = "Nome do Usuário que está em Rota", example = "Bino", required = true)
    private String nomeUsuario;
    @Schema(description = "Tipo do Usuário", example = "Colaborador", required = true)

    private Perfil tipoUsuario;
}
