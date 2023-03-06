package br.com.logisticadbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
public class PostoCreateDTO {
    private int idRota;
    private String nomePosto;
    private double valorCombustível;

}
