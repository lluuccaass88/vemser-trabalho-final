package br.com.logisticadbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "VIAGEM")
public class ViagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VIAGEM_SEQ")
    @SequenceGenerator(name = "VIAGEM_SEQ", sequenceName = "seq_viagem", allocationSize = 1)
    @Column(name = "id_viagem")
    private Integer idViagem;

    @Column(name = "id_viagem")
    private Integer idCaminhao;

    @Column(name = "id_viagem")
    private Integer idRota;

    @Column(name = "id_viagem")
    private Integer IdUsuario;

    //Teste
    //    private Usuario usuario;
    // TODO ENUM OU ATRIBUTO INTEGER/STRING
    @Column(name = "finalizada")
    private Integer finalizada;


    /*private Caminhao caminhao;
    //private Rota rota;

    //TEste
    private ViagemUsuarioDTO usuario;
    private ViagemRotaDTO rota;*/

}
