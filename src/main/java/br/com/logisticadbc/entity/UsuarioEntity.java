package br.com.logisticadbc.entity;

import br.com.logisticadbc.entity.enums.StatusUsuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "USUARIO")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "seq_usuario", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nome")
    private String nome;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "senha")
    private String senha;

    @Column(name = "email")
    private String email;

    @Column(name = "status_usuario")
    private StatusUsuario statusUsuario; // 0 - INATIVO | 1 - ATIVO

    // TODO RELACIONAMENTO COM MOTORISTA

    // TODO RELACIONAMENTO COM COLABORADOR
}
