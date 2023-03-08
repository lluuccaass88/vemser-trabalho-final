package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.Perfil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UsuarioCreateDTO {

    @NotEmpty(message = "nome não pode ser vazio")
    @NotBlank(message = "nome não pode ser em branco")
    @Schema(description = "Nome da Pessoa", example = "Marcklen Guimarães", required = true)
    private String nome;

    @NotEmpty(message = "nome não pode ser vazio")
    @NotBlank(message = "nome não pode ser em branco")
    @Schema(description = "Usuário que será logado no sistema", example = "marcklen", required = true)
    private String usuario;

    @NotEmpty(message = "nome não pode ser vazio")
    @NotBlank(message = "nome não pode ser em branco")
    @Schema(description = "Senha do usuário", example = "123abc", required = true)
    private String senha;

    @NotEmpty(message = "nome não pode ser vazio")
    @NotBlank(message = "nome não pode ser em branco")
    @Schema(description = "Perfil do Funcionário", example = "Colaborador ou Motorista", required = true)
    private Perfil perfil; // 1 - Colaborador, 2 - Motorista

    @Size(max = 11, min = 11, message = "cpf deve conter 11 caracteres")
    @NotEmpty(message = "nome não pode ser vazio")
    @NotBlank(message = "nome não pode ser em branco")
    @Schema(description = "Senha do usuário", example = "123abc", required = true)
    private String cpf;

    @Size(max = 11, min = 11, message = "cpf deve conter 11 caracteres")
    @NotEmpty(message = "nome não pode ser vazio")
    @NotBlank(message = "nome não pode ser em branco")
    @Schema(description = "Senha do usuário", example = "123abc", required = true)
    private String cnh;

    @NotNull(message = "email não pode ser nulo")
    @NotBlank(message = "email não pode ser em branco")
    @Schema(description = "E-mail do Funcionário", example = "marcklen@hotmail.com", required = true)
    private String email;
}