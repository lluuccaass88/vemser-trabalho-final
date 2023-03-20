package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.ColaboradorCreateDTO;
import br.com.logisticadbc.entity.enums.StatusGeral;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ColaboradorDTO extends ColaboradorCreateDTO {

    @Schema(description = "Id de usuario", example = "1")
    private Integer idUsuario;

    @Schema(description = "Status do usuário", example = "FINALIZADA")
    private StatusGeral status;

    @Override
    public void setSenha(@NotNull @NotBlank String senha) {
        super.setSenha("******");
    }
}