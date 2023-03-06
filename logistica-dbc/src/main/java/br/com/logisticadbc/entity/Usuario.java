package br.com.logisticadbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    private Integer id;
    private String nome;
    private String usuario;
    private String senha;
    private Perfil perfil; // 1 - Colaborador, 2 - Motorista
    private String cpf;
    private String cnh;
}
