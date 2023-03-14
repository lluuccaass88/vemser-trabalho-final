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
@Entity(name = "POSTO")
public class PostoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTO_SEQ")
    @SequenceGenerator(name = "POSTO_SEQ", sequenceName = "seq_posto", allocationSize = 1)
    @Column(name = "id_posto")
    private Integer idPosto;

//    @Column(name = "idrota")
    private Integer idRota;

    @Column(name = "nomeposto")
    private String nomePosto;

    @Column(name = "valorcombustível")
    private Double valorCombustível;
}