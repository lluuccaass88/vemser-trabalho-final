package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Posto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
public class RotaCreateDTO {

    @NotBlank
    @Schema(description = "Descrição da rota", example = "Rota de São Paulo até Brasilia", required = true)
    private String descricao;
    @NotBlank
    @Schema(description = "Cidade de partida da rota", example = "São Paulo", required = true)
    private String localPartida;
    @NotBlank
    @Schema(description = "Cidade de destino da rota", example = "Brasilia", required = true)
    private String localDestino;
    @NotNull
    @Schema(description = "Array com os ids dos postos presentes nesta rota", example = "11, 7, 8", required = true)
    private ArrayList<Integer> listaIdPostoCadastrado = new ArrayList();
}




























