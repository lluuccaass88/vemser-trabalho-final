package br.com.logisticadbc.entity;

import br.com.logisticadbc.entity.enums.StatusMotorista;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "MOTORISTA")
public class MotoristaEntity extends UsuarioEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOTORISTA_SEQ")
    @SequenceGenerator(name = "MOTORISTA_SEQ", sequenceName = "seq_motorista", allocationSize = 1)
    @Column(name = "id_motorista")
    private Integer idMotorista;

    @Column(name = "cnh", unique = true)
    private String cnh;

    @Column(name = "status_motorista")
    private StatusMotorista statusMotorista; // 0 - DISPONIVEL, 1 - EM_ESTRADA

    //RELACIONAMENTO COM VIAGEM
    // regra de negócio -> one to many -> um motorista pode ter muitas viagens
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "motorista", orphanRemoval = true)
    @JsonIgnore
    private Set<ViagemEntity> viagens;
}