package br.com.logisticadbc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "COLABORADOR")
public class ColaboradorEntity extends UsuarioEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COLABORADOR_SEQ")
    @SequenceGenerator(name = "COLABORADOR_SEQ", sequenceName = "seq_colaborador", allocationSize = 1)
    @Column(name = "id_colaborador")
    private Integer idColabolador;

    @Column(name = "cpf", unique = true)
    private String cpf;

    //RELACIONAMENTO COM CAMINHAO
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "colaborador", orphanRemoval = true)
    @JsonIgnore
    private Set<CaminhaoEntity> caminhoes;

    //RELACIONAMENTO COM ROTA
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "colaborador", orphanRemoval = true)
    @JsonIgnore
    private Set<RotaEntity> rotas;

    //RELACIONAMENTO COM POSTO
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "colaborador", orphanRemoval = true)
    @JsonIgnore
    private Set<PostoEntity> postos;

}
