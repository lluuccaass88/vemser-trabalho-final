package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.*;
import br.com.logisticadbc.entity.*;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.CaminhaoRepository;
import br.com.logisticadbc.repository.ViagemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ViagemService {
    private final CaminhaoService caminhaoService;
    private final ViagemRepository viagemRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final UsuarioService usuarioService;
    private final RotaService rotaService;



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
                // metodo utilizado para fazer as conversões e tentar buscar o usuario e a rota
                Usuario user = usuarioService.getUsuario(viagemAdicionada.getIdUsuario());
                UsuarioDTO usuario = objectMapper.convertValue(user, UsuarioDTO.class);
                Rota rotaBuscar = caminhaoService.buscarViagem(viagemAdicionada.getIdCaminhao()).getRota();
                RotaDTO rota = objectMapper.convertValue(rotaBuscar, RotaDTO.class);
                emailService.enviarEmailParaMotoristaComRota(usuario, rota);
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

    public List<ViagemDTO> listarViagens() {
        try {
            return viagemRepository.listar().stream()
                    .map(pessoa -> objectMapper.convertValue(pessoa, ViagemDTO.class))
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<PostoDTO> listarPostosEmViagem(int iDviagem){ //Se der tempo fazer isso para fazer verificação
        return null;
    }

    public Viagem getViagem(Integer id) throws Exception {
        return viagemRepository.listar().stream()
                .filter(u -> u.getIdViagem() == id)
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Caminhão não encontrado"));
    }

}
