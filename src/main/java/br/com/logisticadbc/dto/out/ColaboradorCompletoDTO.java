package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
public class ColaboradorCompletoDTO {

    private Integer idUsuario;
    private String nome;
    private String usuario;
    private String email;
    private StatusUsuario statusUsuario;
    private String cpf;

//    private Integer idCaminhao;
//    private String modelo;
//    private String placa;
//    private Integer nivelCombustivel;
//    private StatusCaminhao statusCaminhao;

    Set<CaminhaoDTO> caminhaoes;

    private Integer idRota;
    private String descricao;
    private String localPartida;
    private String localDestino;

    private Integer idPosto;
    private String nomePosto;
    private BigDecimal valorCombustivel;





}
