package br.com.logisticadbc.entity;

import br.com.logisticadbc.entity.enums.StatusCaminhao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "CAMINHAO")
public class CaminhaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMINHAO_SEQ")
    @SequenceGenerator(name = "CAMINHAO_SEQ", sequenceName = "seq_caminhao", allocationSize = 1)
    @Column(name = "id_caminhao")
    private Integer idCaminhao;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "placa")
    private String placa;

    @Column(name = "nivel_combustivel")
    private Integer nivelCombustivel;

    @Column(name = "status_caminhao")
    private StatusCaminhao statusCaminhao; // 0 - estacionado | 1 - em viagem

    // TODO RELACIONAMENTO COM COLABORADOR
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_colaborador")
    @JsonIgnore
    private ColaboradorEntity colaborador;

    // TODO RELACIONAMENTO COM VIAGEM
}