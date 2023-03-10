package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.PostoDTO;
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

import java.util.List;

@RequiredArgsConstructor
@Service
public class ViagemService {
    private final CaminhaoService caminhaoService;
    private final ViagemRepository viagemRepository;
    private final ObjectMapper objectMapper;


    public ViagemDTO adicionarViagem(ViagemCreateDTO viagem) throws BancoDeDadosException {
        try {
            Caminhao caminhaoRecuperado = caminhaoService.getCaminhao(viagem.getIdCaminhao());
            Viagem viagemAdicionada;
            if(caminhaoRecuperado.getEmViagem() == EmViagem.EM_VIAGEM){
                throw new RegraDeNegocioException("O caminhão escolhido já esta em uma viagem."); //Pq eu consegui usar sem passar ele no método?
            }else{

                caminhaoRecuperado.setEmViagem(EmViagem.EM_VIAGEM);

                caminhaoService.editar(viagem.getIdCaminhao(), objectMapper.convertValue(caminhaoRecuperado, CaminhaoCreateDTO.class));

                Viagem viagemEntity = objectMapper.convertValue(viagem, Viagem.class);

                viagemEntity.setFinalizada(false);

                viagemAdicionada = viagemRepository.adicionar(viagemEntity);
            }
            return objectMapper.convertValue(viagemAdicionada, ViagemDTO.class);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Erro no banco de dados.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRO SQL-> " + e.getMessage());
        }

        return null;
    }

//    public List<ViagemDTO> listarViagens() {
//        try {
//            List<Viagem> listar = viagemRepository.listar();
//            listar.forEach(System.out::println);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
//    }


    public List<PostoDTO> listarPostosEmViagem(int iDviagem){
        return null;
    }

    public Viagem getViagem(Integer id) throws Exception {
        return viagemRepository.listar().stream()
                .filter(u -> u.getIdViagem() == id)
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Caminhão não encontrado"));
    }

}
