package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.UsuarioCreateDTO;
import br.com.logisticadbc.entity.enums.StatusGeral;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioDTO extends UsuarioCreateDTO {

    private Integer idUsuario;

    private StatusGeral status;

    @Override
    public void setSenha(@NotNull @NotBlank String senha) {
        super.setSenha("******");
    }

}
