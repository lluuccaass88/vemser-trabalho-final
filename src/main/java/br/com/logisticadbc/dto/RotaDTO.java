package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.PostoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
public class RotaDTO extends RotaCreateDTO{

    @Schema(description = "ID da Rota", example = "1", required = true)
    private Integer idRota;

    @Schema(description = "ID do Colaborador que Criou a Rota", example = "1", required = true)
    private Integer idColaborador;

}