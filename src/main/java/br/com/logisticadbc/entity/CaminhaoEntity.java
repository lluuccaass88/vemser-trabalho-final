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
@Entity(name = "CAMINHAO")
public class CaminhaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAMINHAO_SEQ")
    @SequenceGenerator(name = "CAMINHAO_SEQ", sequenceName = "seq_caminhao", allocationSize = 1)
    @Column(name = "id_caminhao")
    private Integer idCaminhao;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "placa")
    private String placa;

    @Column(name = "gasolina")
    private Integer gasolina;

    @Column(name = "emviagem")
    private EmViagem emViagem; // 1 - estacionado | 2 - em viagem
}