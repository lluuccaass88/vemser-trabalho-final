package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Posto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;

@Data
public class RotaDTO extends RotaCreateDTO{
    @Schema(description = "id da rota", example = "1", required = true)
    private int idRota;
    private ArrayList<Posto> listaPostoCadastrado = new ArrayList();
}
