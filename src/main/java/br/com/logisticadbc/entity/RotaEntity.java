package br.com.logisticadbc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "ROTA")
public class RotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROTA_SEQ")
    @SequenceGenerator(name = "ROTA_SEQ", sequenceName = "seq_rota", allocationSize = 1)
    @Column(name = "id_rota")
    private Integer idRota;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "local_partida")
    private String localPartida;

    @Column(name = "local_destino")
    private String localDestino;

    // TODO RELACIONAMENTO COM COLABORADOR

    // TODO RELACIONAMENTO COM POSTO

}
