package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.ViagemCreateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ViagemDTO extends ViagemCreateDTO {

    @Schema(description = "id de viagem", example = "5", required = true)
    private Integer idViagem;

    @Schema(description = "variavel para representar se a viagem esta ou não finalizada", example = "true ou false", required = true)
    private Integer finalizada;

    @Schema(description = "Placa do camihão vinculado a viagem", example = "YAT0992", required = true)
    private String placaCaminhao;

    @Schema(description = "Nome do motorista vinculado a viagem", example = "Bino", required = true)
    private String nomeMotorista;

    @Schema(description = "local de partida da viagem", example = "Porto Alegre", required = true)
    private String LocalPartida;

    @Schema(description = "local de destino da viagem", example = "Fortaleza", required = true)
    private String LocalDestino;


//    @Schema(description = "Objeto de caminhão vinculado com viagem", example = "Objeto de caminhão", required = true)
//    private CaminhaoEntity caminhao;
//
//    @Schema(description = "Objeto de usuarioViagemDTO vinculado com viagem", example = "Objeto de usuario porém somente com alguns dados", required = true)
//    private ViagemUsuarioDTO usuario;
//    @Schema(description = "Objeto de usuarioRotaDTO vinculado com viagem", example = "Objeto de rota porém somente com alguns dados", required = true)
//    private ViagemRotaDTO rota;
}


