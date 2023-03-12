package br.com.logisticadbc.entity;

import br.com.logisticadbc.dto.ViagemRotaDTO;
import br.com.logisticadbc.dto.ViagemUsuarioDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
//import lombok.NoArgsConstructor;

@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Viagem {
    private int idCaminhao;
    private int idRota;

    private int IdUsuario;

    private int idViagem;
    private Caminhao caminhao;
    //private Rota rota;

    //TEste
    private ViagemUsuarioDTO usuario;
    private ViagemRotaDTO rota;
    //Teste

    //    private Usuario usuario;
    private boolean finalizada;


}
