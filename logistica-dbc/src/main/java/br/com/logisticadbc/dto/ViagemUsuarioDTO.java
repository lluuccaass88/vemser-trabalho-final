package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Perfil;
import lombok.Data;

@Data
public class ViagemUsuarioDTO {
    private int idUsuario;
    private String nomeUsuario;
    private Perfil tipoUsuario;
}
