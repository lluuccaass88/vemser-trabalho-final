package br.com.logisticadbc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "POSTO")
public class PostoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTO_SEQ")
    @SequenceGenerator(name = "POSTO_SEQ", sequenceName = "seq_posto", allocationSize = 1)
    @Column(name = "id_posto")
    private Integer idPosto;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor_combustivel")
    private BigDecimal valorCombustivel;

    // TODO RELACIONAMENTO COM COLABORADOR
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_colaborador")
    @JsonIgnore
    private ColaboradorEntity colaborador;

    // TODO RELACIONAMENTO COM ROTA
}