package br.com.logisticadbc.entity;
import br.com.logisticadbc.entity.Posto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rota {
    private int idRota;
    private String descricao;
    private String localPartida;
    private String localDestino;
    private ArrayList<Posto> listaPostoCadastrado = new ArrayList();
    private ArrayList<Integer> listaIdPostoCadastrado = new ArrayList();

    public void setListaPostoCadastradoPosto(Posto posto) {
        this.listaPostoCadastrado.add(posto);
    }
}
