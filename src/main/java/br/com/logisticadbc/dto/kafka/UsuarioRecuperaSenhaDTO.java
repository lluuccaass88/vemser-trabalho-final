package br.com.logisticadbc.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRecuperaSenhaDTO {

    private String email;
    private String nome;
    private String senha;
}
