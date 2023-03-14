package br.com.logisticadbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Posto {
    private Integer idPosto;
    private Integer idRota;
    private String nomePosto;
    private Double valorCombustível;
}