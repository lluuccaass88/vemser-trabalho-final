package br.com.logisticadbc.dto.out;

import br.com.logisticadbc.entity.enums.StatusMotorista;
import br.com.logisticadbc.entity.enums.StatusUsuario;
import br.com.logisticadbc.entity.enums.StatusViagem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MotoristaCompletoDTO {
    private Integer idUsuario;
    private String nome;
    private String usuario;
    private String email;
    private StatusUsuario statusUsuario;
    private String cnh;
    private StatusMotorista statusMotorista;

    private Integer idViagem;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusViagem statusViagem;

}
