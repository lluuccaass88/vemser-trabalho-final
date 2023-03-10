package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.ViagemCreateDTO;
import br.com.logisticadbc.dto.ViagemDTO;
import br.com.logisticadbc.entity.Caminhao;
import br.com.logisticadbc.entity.EmViagem;
import br.com.logisticadbc.entity.Viagem;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.CaminhaoRepository;
import br.com.logisticadbc.repository.ViagemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ViagemService {
    private final CaminhaoService caminhaoService;
    private final ViagemRepository viagemRepository;
    private final ObjectMapper objectMapper;


    public ViagemDTO adicionarViagem(ViagemCreateDTO viagem) throws BancoDeDadosException {
        try {
            Viagem viagemEntity = objectMapper.convertValue(viagem, Viagem.class);

            viagemEntity.setFinalizada(false);

            Viagem viagemAdicionada = viagemRepository.adicionar(viagemEntity);

            Caminhao caminhaoRecuperado = caminhaoService.getCaminhao(viagem.getIdCaminhao());

            if(caminhaoRecuperado.getEmViagem() == EmViagem.EM_VIAGEM){
                throw new RegraDeNegocioException("O caminhão escolhido já esta em uma viagem."); //Pq eu consegui usar sem passar ele no método?
            }else{
                caminhaoService.editar(viagem.getIdCaminhao());
            }
            return objectMapper.convertValue(viagemAdicionada, ViagemDTO.class);

        } catch (BancoDeDadosException e) {
            throw new BancoDeDadosException("Erro no banco de dados.");
        } catch (Exception e) {
            System.out.println("ERRO SQL-> " + e.getMessage());
        }

        return null;
    }
}
