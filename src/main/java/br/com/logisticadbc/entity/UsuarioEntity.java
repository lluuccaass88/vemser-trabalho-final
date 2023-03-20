package br.com.logisticadbc.entity;

import br.com.logisticadbc.entity.enums.StatusGeral;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(of = {"idUsuario"})
public abstract class UsuarioEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "seq_usuario", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nome", length = 100)
    @Size(min = 2, max = 100, message = "Nome da Pessoa deve ter entre 2 e 100 letras")
    private String nome;

    @Column(name = "usuario")
    @Size(min = 2, max = 50, message = "Login do Usuário deve ter entre 2 e 50 caracteres")
    private String usuario;

    @Column(name = "senha")
    @Size(min = 2, max = 20, message = "Senha do Usuário deve ter entre 2 e 20 caracteres")
    private String senha;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private StatusGeral status; // 0 - INATIVO | 1 - ATIVO

    //Classe extendida por MOTORISTA e COLABORADOR
}