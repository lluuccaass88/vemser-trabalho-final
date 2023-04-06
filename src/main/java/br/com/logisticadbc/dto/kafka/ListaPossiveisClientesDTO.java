package br.com.logisticadbc.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaPossiveisClientesDTO {

    private List<PossiveisClientesDTO> listaPossiveisClientes;
}
