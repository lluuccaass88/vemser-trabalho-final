package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.dto.in.CargoCreateDTO;
import lombok.Data;

@Data
public class CargoDTO extends CargoCreateDTO {

    private Integer idCargo;

}
