package br.com.logisticadbc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ColaboradorCreateDTO {

    //    @NotEmpty(message = "Nome não pode ser vazio")
//    @NotBlank(message = "Nome não pode ser em branco")
    @Schema(description = "Nome da Pessoa", example = "Marcklen Guimarães", required = true)
    private String nome;

    //    @NotEmpty(message = "Usuário não pode ser vazio")
//    @NotBlank(message = "Usuário não pode ser em branco")
    @Schema(description = "Usuário que será logado no sistema", example = "marcklen", required = true)
    private String usuario;

    //    @NotEmpty(message = "Senha não pode ser vazio")
//    @NotBlank(message = "Senha não pode ser em branco")
    @JsonIgnore
    @Schema(description = "Senha do usuário", example = "123abc", required = true)
    private String senha;


    //    @NotNull(message = "email não pode ser nulo")
//    @NotBlank(message = "email não pode ser em branco")
    @Schema(description = "E-mail do Funcionário", example = "marcklen@hotmail.com", required = true)
    private String email;

    //    @Size(max = 11, min = 11, message = "cpf deve conter 11 caracteres")
//    @NotEmpty(message = "CPF não pode ser vazio")
//    @NotBlank(message = "CPF não pode ser em branco")
    @Schema(description = "CPF do usuário", example = "123abc", required = true)
    private String cpf;
}