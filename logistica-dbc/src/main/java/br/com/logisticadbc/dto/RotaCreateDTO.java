package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Posto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
public class RotaCreateDTO {
    @NotNull
    private int idRota;
    @NotBlank
    private String descricao;
    @NotBlank
    private String localPartida;
    @NotBlank
    private String localDestino;
    private ArrayList<Posto> listaPostoCadastrado = new ArrayList();
}
