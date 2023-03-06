package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Perfil;
import lombok.Data;

@Data
public class UsuarioCreateDTO {

    private String nome;
    private String usuario;
    private String senha;
    private Perfil perfil; // 1 - Colaborador, 2 - Motorista
    private String cpf;
    private String cnh;
}
