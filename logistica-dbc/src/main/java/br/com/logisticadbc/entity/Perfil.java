package br.com.logisticadbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Perfil {

    COLABORADOR(1), MOTORISTA(2);

    private Integer perfil;

    public static Perfil ofTipoPerfil(Integer tipo) {
        return Arrays.stream(Perfil.values())
                .filter(tp -> tp.getPerfil().equals(tipo))
                .findFirst().get();
    }
}
