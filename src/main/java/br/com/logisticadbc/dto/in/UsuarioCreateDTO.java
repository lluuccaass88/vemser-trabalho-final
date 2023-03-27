package br.com.logisticadbc.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UsuarioCreateDTO {

    @NotNull
    @NotBlank
    @Schema(description = "Login de acesso", example = "bino")
    private String login;

    @NotNull
    @NotBlank
    @Schema(description = "Senha de acesso", example = "abc123")
    private String senha;

    @NotNull
    @NotBlank
    @Schema(description = "Nome", example = "Bino da Silva")
    private String nome;

    @NotNull
    @NotBlank
    @Schema(description = "Email", example = "bino@email.com")
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 11, max = 11, message = "Deve conter 11 caracteres")
    @Schema(description = "Documento de identificação(CPF/CNH)", example = "12345678910")
    private String documento;

}
