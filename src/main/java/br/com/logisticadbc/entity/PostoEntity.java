package br.com.logisticadbc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

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

    //RELACIONAMENTO COM COLABORADOR
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @JsonIgnore
    private ColaboradorEntity colaborador;

    //RELACIONAMENTO COM ROTA
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "postos")
    @JsonIgnore
    private Set<RotaEntity> rotas;
}