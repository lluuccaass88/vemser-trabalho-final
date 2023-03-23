package br.com.logisticadbc.entity.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum StatusViagem {

    EM_ANDAMENTO("EM_ANDAMENTO"),
    FINALIZADA("FINALIZADA");

    private String descricao;

    StatusViagem(String descricao) {
        this.descricao = descricao;
    }

    // retorna string de descriçao para salvar no banco como string
    public static StatusViagem getDescricao(String descricao) {
        return Arrays.stream(StatusViagem.values())
                .filter(desc -> desc.getDescricao().equals(descricao))
                .findFirst()
                .get();
    }
}
