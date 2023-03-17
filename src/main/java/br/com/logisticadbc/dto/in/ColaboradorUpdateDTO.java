package br.com.logisticadbc.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ColaboradorUpdateDTO {

    @NotNull
    @NotBlank
    @Schema(description = "Nome do Usuário", example = "Marcklen Guimarães", required = true)
    private String nome;

    @NotNull
    @NotBlank
    @Schema(description = "Senha do usuário", example = "123abc")
    private String senha;
}