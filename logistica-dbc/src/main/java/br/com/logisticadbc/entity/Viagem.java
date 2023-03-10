package br.com.logisticadbc.entity;

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
    private Rota rota;
    private Usuario usuario;
    private boolean finalizada;

    //public Viagem() { this.finalizada = 0; } // 0 - em viagem e 1 - finalizada

//    @Override
//    public String toString() {
//        if(this.finalizada == 1 ){
//            return "Viagem{" +
//                    "id Viagem = " + idViagem +
//                    ", Placa do caminhao = " + caminhao.getPlaca() +
//                    ", rota = " + rota.getDescricao() +
//                    ", usuario = " + usuario.getNome() +
//                    ", viagem finalizada }";
//        }else{
//            return "Viagem{" +
//                    "idViagem=" + idViagem +
//                    ", Placa do caminhao = " + caminhao.getPlaca() +
//                    ", rota = " + rota.getDescricao() +
//                    ", usuario = " + usuario.getNome() +
//                    ", viagem em andamento }";
//        }
//    }
}
