package br.com.logisticadbc.entity.enums;

import lombok.Getter;

@Getter
public enum StatusCaminhao {

    ESTACIONADO, EM_VIAGEM

//    private Integer opcao;

    /*public static StatusCaminhao getOpcaoEmViagem(Integer opcao) { // 1
        return Arrays.stream(StatusCaminhao.values()) // [ESTACIONADO(1), EM_VIAGEM(2)]
                .filter(emViagem -> emViagem.getOpcao().equals(opcao)) // [ESTACIONADO(1)]
                .findFirst() // optional => ESTACIONADO(1)
                .get(); // ESTACIONADO(1)
    }*/
}