package br.com.logisticadbc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "COLABORADOR")
//@EqualsAndHashCode(callSuper = false)
public class ColaboradorEntity extends UsuarioEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "cpf", unique = true)
    private String cpf;

    //RELACIONAMENTO COM CAMINHAO
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "colaborador", orphanRemoval = true)
    @JsonIgnore
    private Set<CaminhaoEntity> caminhoes;

    //RELACIONAMENTO COM ROTA
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "colaborador")
    @JsonIgnore
    private Set<RotaEntity> rotas;

    //RELACIONAMENTO COM POSTO
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "colaborador")
    @JsonIgnore
    private Set<PostoEntity> postos;
}
