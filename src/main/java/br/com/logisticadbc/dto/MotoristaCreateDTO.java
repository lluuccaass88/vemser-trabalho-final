package br.com.logisticadbc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MotoristaCreateDTO {

    @NotNull
    @NotBlank
    @Schema(description = "Nome da Motorista", example = "Bino", required = true)
    private String nome;

    @NotNull
    @NotBlank
    @Schema(description = "Usuario que será usado no sistema", example = "bino", required = true)
    private String usuario;

    @NotNull
    @NotBlank
    @Schema(description = "Senha que será usada no sistema", example = "cilada123", required = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @NotNull
    @NotBlank
    @Schema(description = "Email para cadastro no sistema", example = "bino@ehUmaCilada.com", required = true)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 11, max = 11, message = "CNH deve conter 11 caracteres")
    @Schema(description = "CNH motorista para realizar viagens", example = "14525658963", required = true)
    private String cnh;

    @NotNull
    @Schema(description = "Status atual do Motorista", example = "0 - Disponivel ou 1 - Em Estrada", required = true)
    private Integer status_motorista;
}