package br.com.logisticadbc.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {

    @NotNull
    @Schema(description = "login", example = "meulogin")
    private String login;

    @NotNull
    @Schema(description = "senha", example = "minhasenha")
    private String senha;
}
