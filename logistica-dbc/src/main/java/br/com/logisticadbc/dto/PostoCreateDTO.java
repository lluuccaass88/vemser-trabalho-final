package br.com.logisticadbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PostoCreateDTO {
    @NotNull
    private int idRota;
    @NotBlank
    private String nomePosto;
    @NotNull
    private double valorCombustível;

}
