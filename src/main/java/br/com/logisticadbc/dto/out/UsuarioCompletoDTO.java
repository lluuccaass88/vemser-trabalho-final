package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.StatusViagem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UsuarioCompletoDTO {

    private Integer idUsuario;
    private String login;
    private String nomeUsuario;
    private String email;
    private String documento;
    private StatusGeral statusUsuario;

    private Integer idCargo;
    private String nome;

    private Integer idCaminhao;
    private String modelo;
    private String placa;
    private Integer nivelCombustivel;
    private StatusCaminhao statusCaminhao;
    private StatusGeral statusGeralCaminhao;

    private Integer idRota;
    private String descricaoRota;
    private String localPartida;
    private String localDestino;
    private StatusGeral statusRota;

    private Integer idViagem;
    private String descricaoViagem;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusViagem statusViagem;
}
