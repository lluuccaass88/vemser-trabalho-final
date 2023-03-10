package br.com.logisticadbc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioDTO extends UsuarioCreateDTO{
    @Schema(description = "Id de usuario", example = "5,89", required = true)
    private Integer id;
}