package br.com.logisticadbc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "MOTORISTA")
public class MotoristaEntity extends UsuarioEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOTORISTA_SEQ")
    @SequenceGenerator(name = "MOTORISTA_SEQ", sequenceName = "seq_motorista", allocationSize = 1)
    @Column(name = "id_motorista")
    private Integer idMotorista;

    @Column(name = "cnh")
    private String cnh;

    // TODO RELACIONAMENTO COM USUARIO

    // TODO RELACIONAMENTO COM VIAGEM
}
