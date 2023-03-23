package br.com.logisticadbc.entity.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StatusCaminhao {

    ESTACIONADO("ESTACIONADO"),
    EM_VIAGEM("EM_VIAGEM");

    private String descricao;

    StatusCaminhao(String descricao) {
        this.descricao = descricao;
    }

    // retorna string de descriçao para salvar no banco como string
    public static StatusCaminhao getDescricao(String descricao) {
        return Arrays.stream(StatusCaminhao.values())
                .filter(desc -> desc.getDescricao().equals(descricao))
                .findFirst()
                .get();
    }
}

