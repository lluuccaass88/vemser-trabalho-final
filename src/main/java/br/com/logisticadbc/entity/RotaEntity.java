package br.com.logisticadbc.entity;

import br.com.logisticadbc.entity.enums.StatusGeral;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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

    @Column(name = "status")
    private StatusGeral status; // 0 - INATIVO | 1 - ATIVO

    //RELACIONAMENTO COM COLABORADOR
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @JsonIgnore
    private UsuarioEntity usuario;

    //RELACIONAMENTO COM POSTO
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ROTA_X_POSTO",
            joinColumns = @JoinColumn(name = "id_rota"),
            inverseJoinColumns = @JoinColumn(name = "id_posto"))
    @JsonIgnore
    private Set<PostoEntity> postos;

    //RELACIONAMENTO COM VIAGEM
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rota")
    @JsonIgnore
    private Set<ViagemEntity> viagens;

}
