package br.com.logisticadbc.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoCreateDTO {

    @NotNull
    @NotBlank
    @Schema(description = "Nome do cargo", example = "ROLE_ADMIN")
    private String nome;

}
