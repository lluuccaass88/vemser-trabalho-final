package br.com.logisticadbc.dto;

import br.com.logisticadbc.entity.EmViagem;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CaminhaoCreateDTO {
    @NotBlank
    private String modelo;
    @NotBlank
    private String placa;
    @NotNull
    private Integer gasolina;
    @NotNull
    private EmViagem emViagem; // 1 - estacionado | 2 - em viagem
}
