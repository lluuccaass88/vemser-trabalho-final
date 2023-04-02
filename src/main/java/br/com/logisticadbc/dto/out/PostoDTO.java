package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.entity.enums.StatusGeral;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostoDTO{

    @Schema(description = "id do posto")
    private String id;

    @Schema(description = "Nome do posto")
    private String nome;

    @Schema(description = "Coordenadas")
    private GeoJsonPoint location;

    @Schema(description = "Cidade em que está o posto")
    private String cidade;

    @Schema(description = "Valor do combustivel no posto")
    private Double valorCombustivel;

    @Schema(description = "Se o posto está ativo")
    private StatusGeral status;
}
