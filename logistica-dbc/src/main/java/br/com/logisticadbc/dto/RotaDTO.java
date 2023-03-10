package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Posto;
import lombok.Data;

import java.util.ArrayList;

@Data
public class RotaDTO extends RotaCreateDTO{

    private int idRota;
    private ArrayList<Posto> listaPostoCadastrado = new ArrayList();
}
