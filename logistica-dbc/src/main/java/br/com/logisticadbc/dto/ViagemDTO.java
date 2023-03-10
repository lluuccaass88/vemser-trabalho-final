package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Caminhao;
import br.com.logisticadbc.entity.Rota;
import br.com.logisticadbc.entity.Usuario;
import lombok.Data;

@Data
public class ViagemDTO extends ViagemCreateDTO{

    private int idViagem;
    private Caminhao caminhao;
    private Rota rota;
    private Usuario usuario;
    private boolean finalizada;
}
