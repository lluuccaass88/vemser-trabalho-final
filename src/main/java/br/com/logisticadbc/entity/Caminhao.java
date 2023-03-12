package br.com.logisticadbc.entity;

import br.com.logisticadbc.dto.CaminhaoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Caminhao {

    private Integer idCaminhao;
    private String modelo;
    private String placa;
    private Integer gasolina;
    private EmViagem emViagem; // 1 - estacionado | 2 - em viagem
}