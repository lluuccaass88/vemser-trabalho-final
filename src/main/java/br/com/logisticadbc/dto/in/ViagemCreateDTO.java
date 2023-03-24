package br.com.logisticadbc.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ViagemCreateDTO {

    @NotNull
    @Schema(description = "Descrição da viagem", example = "Viagem longa com duas paradas")
    private String descricao;

    @NotNull
    @FutureOrPresent(message = "O campo dataInicio deve ser atual ou futuro!")
    @Schema(description = "Data partida", example = "2023-03-03")
    private LocalDate dataInicio;

    @NotNull
    @FutureOrPresent(message = "O campo dataFim deve ser atual ou futuro!")
    @Schema(description = "data_fim", example = "2023-03-04")
    private LocalDate dataFim;

    @NotNull
    @Schema(description = "Id do caminhão vinculado com a viagem", example = "1")
    private Integer idCaminhao;

    @NotNull
    @Schema(description = "Id da rota vinculada com a viagem", example = "1")
    private Integer idRota;

}
