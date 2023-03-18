package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ColaboradorCompletoDTO {

    private Integer idUsuario;
    private String nome;
    private String usuario;
    private String email;
    private StatusGeral statusUsuario;
    private String cpf;

    private Integer idCaminhao;
    private String modelo;
    private String placa;
    private Integer nivelCombustivel;
    private StatusCaminhao statusCaminhao;
    private StatusGeral statusGeralCaminhao;


    private Integer idRota;
    private String descricao;
    private String localPartida;
    private String localDestino;
    private StatusGeral statusRota;

    private Integer idPosto;
    private String nomePosto;
    private BigDecimal valorCombustivel;
    private StatusGeral statusPosto;





}
