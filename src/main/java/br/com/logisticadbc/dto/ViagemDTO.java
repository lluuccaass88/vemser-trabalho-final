package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Caminhao;
import br.com.logisticadbc.entity.Perfil;
import br.com.logisticadbc.entity.Rota;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ViagemDTO  {

    @Schema(description = "id de viagem", example = "5", required = true)
    private int idViagem;

    @Schema(description = "variavel para representar se a viagem esta ou não finalizada", example = "true ou false", required = true)
    private boolean finalizada;

    @Schema(description = "Objeto de caminhão vinculado com viagem", example = "Objeto de caminhão", required = true)
    private Caminhao caminhao;

    @Schema(description = "Objeto de usuarioViagemDTO vinculado com viagem", example = "Objeto de usuario porém somente com alguns dados", required = true)
    private ViagemUsuarioDTO usuario;
    @Schema(description = "Objeto de usuarioRotaDTO vinculado com viagem", example = "Objeto de rota porém somente com alguns dados", required = true)
    private ViagemRotaDTO rota;
}


