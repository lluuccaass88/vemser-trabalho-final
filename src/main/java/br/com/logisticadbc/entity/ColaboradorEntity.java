package br.com.logisticadbc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "COLABORADOR")
public class ColaboradorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COLABORADOR_SEQ")
    @SequenceGenerator(name = "COLABORADOR_SEQ", sequenceName = "seq_colaborador", allocationSize = 1)
    @Column(name = "id_colaborador")
    private Integer idColabolador;

    @Column(name = "cpf")
    private String cpf;

    // TODO RELACIONAMENTO COM USUARIO

    // TODO RELACIONAMENTO COM CAMINHAO

    // TODO RELACIONAMENTO COM ROTA

    // TODO RELACIONAMENTO COM POSTO

    // TODO RELACIONAMENTO COM VIAGEM
}
