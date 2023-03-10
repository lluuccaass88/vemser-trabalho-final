package br.com.logisticadbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
//@Data
@Getter
public enum EmViagem {

    ESTACIONADO(1), EM_VIAGEM(2);

    private Integer opcao;

    public static EmViagem getOpcaoEmViagem(Integer opcao) { // 1
        return Arrays.stream(EmViagem.values()) // [ESTACIONADO(1), EM_VIAGEM(2)]
                .filter(emViagem -> emViagem.getOpcao().equals(opcao)) // [ESTACIONADO(1)]
                .findFirst() // optional => ESTACIONADO(1)
                .get(); // ESTACIONADO(1)
    }
}