package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.entity.enums.StatusGeral;
import lombok.Data;

import java.util.Set;

@Data
public class UsuarioDTO{

    private Integer idUsuario;

    private String login;

//    private String senha;

    private String nome;

    private String email;

    private String documento;

    private StatusGeral status;

    private Set<CargoDTO> cargos;

//    public void setSenha(String senha) {
//        setSenha("******");
//    }
}
