package br.com.logisticadbc.entity;

import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status_caminhao")
    private StatusCaminhao statusCaminhao; // 0 - estacionado | 1 - em viagem

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusGeral status; // 0 - INATIVO | 1 - ATIVO

    //RELACIONAMENTO COM COLABORADOR
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @JsonIgnore
    private UsuarioEntity usuario;

    //RELACIONAMENTO COM VIAGEM
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caminhao")
    @JsonIgnore
    private Set<ViagemEntity> viagens;
}