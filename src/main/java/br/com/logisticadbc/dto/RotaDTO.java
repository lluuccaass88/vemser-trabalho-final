package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.PostoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;

@Data
public class RotaDTO extends RotaCreateDTO{
    @Schema(description = "id da rota", example = "1", required = true)
    private int idRota;
    @Schema(description = "Lista de objetos de postos cadastrados", example = "Objeto de posto", required = true)
    private ArrayList<PostoEntity> listaPostoCadastrado = new ArrayList();
}
