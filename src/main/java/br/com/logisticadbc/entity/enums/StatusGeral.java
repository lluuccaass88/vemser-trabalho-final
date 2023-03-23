package br.com.logisticadbc.entity.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StatusGeral {

    INATIVO("INATIVO"),
    ATIVO("ATIVO");

    private String descricao;

    StatusGeral(String descricao) {
        this.descricao = descricao;
    }

    // retorna string de descriçao para salvar no banco como string
    public static StatusGeral getDescricao(String descricao) {
        return Arrays.stream(StatusGeral.values())
                .filter(desc -> desc.getDescricao().equals(descricao))
                .findFirst()
                .get();
    }

}
