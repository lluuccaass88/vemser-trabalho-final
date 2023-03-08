package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Caminhao;
import br.com.logisticadbc.entity.Rota;
import br.com.logisticadbc.entity.Usuario;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ViagemCreateDTO {
    @NotNull
    private Caminhao caminhao;
    @NotNull
    private Rota rota;
    @NotNull
    private Usuario usuario;
    @NotNull
    private int finalizada;

}
