package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Caminhao;
import br.com.logisticadbc.entity.Rota;
import br.com.logisticadbc.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ViagemDTO extends ViagemCreateDTO{

    @Schema(description = "id de viagem", example = "5", required = true)
    private int idViagem;
    @Schema(description = "Objeto de caminhão vinculado com viagem", example = "Objeto de caminhão", required = true)
    private Caminhao caminhao;
    @Schema(description = "Objeto de rota vinculado com viagem", example = "Objeto de rota", required = true)
    private Rota rota;
    @Schema(description = "Objeto de usuario vinculado com viagem", example = "Objeto de usuario", required = true)
    private Usuario usuario;

    @Schema(description = "variavel para representar se a viagem esta ou não finalizada", example = "true ou false", required = true)
    private boolean finalizada;
}
