package br.com.logisticadbc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostoDTO extends PostoCreateDTO{

    @Schema(description = "id do posto", example = "1", required = true)
    private Integer idPosto;
}
