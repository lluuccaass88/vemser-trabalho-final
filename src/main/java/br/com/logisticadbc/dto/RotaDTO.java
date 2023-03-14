package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.PostoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
public class RotaDTO extends RotaCreateDTO{

    @Schema(description = "id da rota", example = "1", required = true)
    private Integer idRota;

    @Schema(description = "Lista dos nomes dos postos cadastrados na rota", example = "Ipiranga", required = true)
    private ArrayList<String> listaNomePostoCadastrado = new ArrayList();
}
