package br.com.logisticadbc.dto.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ColaboradorCreateDTO {

    @NotNull
    @NotBlank
    @Schema(description = "Nome do Usuário", example = "Marcklen Guimarães")
    private String nome;

    @NotNull
    @NotBlank
    @Schema(description = "Usuário que será logado no sistema", example = "marcklen")
    private String usuario;

    @NotNull
    @NotBlank
    @Schema(description = "Senha do usuário", example = "123abc")
    private String senha;

    @NotNull
    @NotBlank
    @Schema(description = "E-mail do Usuário", example = "marcklen@hotmail.com")
    private String email;

    @NotNull
    @NotBlank
    @Size(max = 11, min = 11, message = "CPF deve conter 11 caracteres")
    @Schema(description = "CPF do Colaborador", example = "12345678901")
    private String cpf;
}