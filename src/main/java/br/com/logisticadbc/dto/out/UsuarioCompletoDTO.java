package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.StatusViagem;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private Integer idPosto;
    private String nomePosto;
    private BigDecimal valorCombustivel;
    private StatusGeral statusPosto;

    private Integer idViagem;
    private String descricaoViagem;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusViagem statusViagem;
}
