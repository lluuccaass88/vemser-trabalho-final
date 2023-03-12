package br.com.logisticadbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Posto {
    private int idPosto;
    private int idRota;
    private String nomePosto;
    private double valorCombustível;
}
