package br.com.logisticadbc.entity;

import br.com.logisticadbc.entity.enums.StatusViagem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "VIAGEM")
public class ViagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VIAGEM_SEQ")
    @SequenceGenerator(name = "VIAGEM_SEQ", sequenceName = "seq_viagem", allocationSize = 1)
    @Column(name = "id_viagem")
    private Integer idViagem;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "status_viagem")
    private StatusViagem statusViagem; // 0 - EM_ANDAMENTO | 1 - FINALIZADA

    // TODO RELACIONAMENTO COM MOTORISTA

    // TODO RELACIONAMENTO COM CAMINHAO

    // TODO RELACIONAMENTO COM ROTA

}
