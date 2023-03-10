package br.com.logisticadbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;

@AllArgsConstructor
//@NoArgsConstructor
@Data
public class Viagem {

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
