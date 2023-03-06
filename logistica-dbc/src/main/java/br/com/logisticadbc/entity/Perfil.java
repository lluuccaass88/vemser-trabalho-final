package br.com.logisticadbc.entity;

import java.util.Arrays;

public enum Perfil {

    COLABORADOR (1), MOTORISTA (2);

    private Integer perfil;

    Perfil(Integer perfil){
        this.perfil = perfil;
    }

    public Integer getPerfil() {
        return perfil;
    }

    public static Perfil ofTipoPerfil(Integer tipo){
        return Arrays.stream(Perfil.values())
                .filter(tp -> tp.getPerfil().equals(tipo))
                .findFirst().get();
    }
}
