/*
package br.com.logisticadbc.service;

import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.MotoristaEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidacaoService {

    private final ColaboradorService colaboradorService;
    private final MotoristaService motoristaService;

    public void validacao(Integer id, String perfil) throws RegraDeNegocioException {

        if (perfil.equals("motorista")) {
            MotoristaEntity motoristaEncontrado = motoristaService.buscarPorId(id);

            if (motoristaEncontrado.getStatus().equals(StatusGeral.INATIVO)) {
                throw new RegraDeNegocioException("Motorista inativo!");
            }

        } else if (perfil.equals("colaborador")) {
            ColaboradorEntity colaboradorEncontrado = colaboradorService.buscarPorId(id);

            if (colaboradorEncontrado.getStatus().equals(StatusGeral.INATIVO)) {
                throw new RegraDeNegocioException("Colaborador inativo!");
            }
        }
    }
}
*/
