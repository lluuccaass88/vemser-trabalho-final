package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Caminhao;
import br.com.logisticadbc.entity.Rota;
import br.com.logisticadbc.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ViagemCreateDTO {
    @NotNull
    @Schema(description = "Id do caminhão vinculado com a viagem", example = "1", required = true)
    private int idCaminhao;
    @NotNull
    @Schema(description = "Id da rota vinculada com a viagem", example = "1", required = true)
    private int idRota;
    @NotNull
    @Schema(description = "Id do usuario vinculado com a viagem", example = "1", required = true)
    private int IdUsuario;
}
