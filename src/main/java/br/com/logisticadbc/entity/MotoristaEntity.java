/*
package br.com.logisticadbc.entity;

import br.com.logisticadbc.entity.enums.StatusMotorista;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MOTORISTA")
@EqualsAndHashCode(callSuper = false)
public class MotoristaEntity extends UsuarioEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "cnh", unique = true)
    private String cnh;

    @Column(name = "status_motorista")
    private StatusMotorista statusMotorista; // 0 - DISPONIVEL, 1 - EM_ESTRADA

    //RELACIONAMENTO COM VIAGEM
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "motorista")
    @JsonIgnore
    private Set<ViagemEntity> viagens;
}
*/
