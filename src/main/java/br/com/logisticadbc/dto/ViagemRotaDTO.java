package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Posto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
@Data
public class ViagemRotaDTO {
    @Schema(description = "id da rota", example = "1", required = true)
    private int idRota;
    @Schema(description = "Cidade de partida da rota", example = "São Paulo", required = true)
    private String localPartida;
    @Schema(description = "Cidade de destino da rota", example = "Brasilia", required = true)
    private String localDestino;
}
