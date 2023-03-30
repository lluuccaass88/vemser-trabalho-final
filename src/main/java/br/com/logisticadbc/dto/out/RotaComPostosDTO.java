/*
package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.entity.enums.StatusGeral;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class RotaComPostosDTO extends RotaCreateDTO {

    @Schema(description = "Descrição da Rota")
    private String descricao;

    @Schema(description = "Destino Inicial da Rota")
    private String localPartida;

    @Schema(description = "Destino Final da Rota")
    private String localDestino;

    @Schema(description = "ID da Rota")
    private Integer idRota;

    @Schema(description = "Status da Rota", example = "ATIVO")
    private StatusGeral status;

    @Schema(description = "ID do Usuário")
    private Integer idUsuario;

    @Schema(description = "Postos cadastrados")
    private Set<PostoDTO> postos;

}
*/
