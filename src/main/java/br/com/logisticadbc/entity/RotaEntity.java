package br.com.logisticadbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ROTA")
public class RotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROTA_SEQ")
    @SequenceGenerator(name = "ROTA_SEQ", sequenceName = "seq_rota", allocationSize = 1)
    @Column(name = "id_rota")
    private Integer idRota;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "localpartida")
    private String localPartida;

    @Column(name = "localdestino")
    private String localDestino;

    // TODO VER IMPLEMENTAÇÃO
    private ArrayList<PostoEntity> listaPostoCadastrado = new ArrayList();
    private ArrayList<Integer> listaIdPostoCadastrado = new ArrayList();

    public void setListaPostoCadastradoPosto(PostoEntity posto) {
        this.listaPostoCadastrado.add(posto);
    }
}
