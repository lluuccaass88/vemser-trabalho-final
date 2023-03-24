package br.com.logisticadbc.entity;

import br.com.logisticadbc.entity.enums.StatusGeral;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "USUARIO")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "seq_usuario", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "login")
    private String login;

    @Column(name = "senha")
    private String senha;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "documento")
    private String documento; // CPF | CNH

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusGeral status; // 0 - INATIVO | 1 - ATIVO

    //RELACIONAMENTO COM CAMINHAO
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    @JsonIgnore
    private Set<CaminhaoEntity> caminhoes;

    //RELACIONAMENTO COM ROTA
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    @JsonIgnore
    private Set<RotaEntity> rotas;

    //RELACIONAMENTO COM POSTO
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    @JsonIgnore
    private Set<PostoEntity> postos;

    //RELACIONAMENTO COM VIAGEM
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    @JsonIgnore
    private Set<ViagemEntity> viagens;

    //RELACIONAMENTO COM CARGO
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CARGO_X_USUARIO",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_cargo"))
    @JsonIgnore
    private Set<CargoEntity> cargos;
}