package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.*;
import br.com.logisticadbc.entity.*;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.exceptions.BancoDeDadosException;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
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


    public ViagemDTO adicionarViagem(ViagemCreateDTO viagem) throws RegraDeNegocioException {
        try {
            CaminhaoEntity caminhaoRecuperado = caminhaoService.getCaminhao(viagem.getIdCaminhao());
            UsuarioEntity usuarioRecuperado = usuarioService.getUsuario(viagem.getIdUsuario());

            ViagemEntity viagemAdicionada;
            if (caminhaoRecuperado.getEmViagem() == StatusCaminhao.EM_VIAGEM) {
                throw new RegraDeNegocioException("O caminhão escolhido já esta em uma viagem no momento.");
            }else if(usuarioRecuperado.getPerfil() == Perfil.COLABORADOR){
                throw new RegraDeNegocioException("Somente um usuario do tipo motorista pode ser associado a uma viagem.");
            }else {

                caminhaoRecuperado.setEmViagem(StatusCaminhao.EM_VIAGEM);
                caminhaoService.editar(viagem.getIdCaminhao(), objectMapper.convertValue(caminhaoRecuperado, CaminhaoCreateDTO.class));

                ViagemEntity viagemEntity = objectMapper.convertValue(viagem, ViagemEntity.class);
                viagemEntity.setFinalizada(false);
                viagemAdicionada = viagemRepository.adicionar(viagemEntity);

                // metodo utilizado para fazer as conversões e tentar buscar o usuario e a rota
                UsuarioEntity user = usuarioService.getUsuario(viagemAdicionada.getIdUsuario());
                UsuarioDTO usuario = objectMapper.convertValue(user, UsuarioDTO.class);
                //Rota rotaBuscar = caminhaoService.buscarViagem(viagemAdicionada.getIdCaminhao()).getRota();
                RotaEntity rotaBuscar = rotaService.getRota(viagemAdicionada.getIdRota()); //Buscando a rota
                RotaDTO rota = objectMapper.convertValue(rotaBuscar, RotaDTO.class);
                //emailService.enviarEmailParaMotoristaComRota(usuario, rota); // Enviando o email para o motorista
                emailService.enviarEmailParaColaboradorComInfoRota(usuario, rota); // Enviando o email para o colaborador
            }
            return objectMapper.convertValue(viagemAdicionada, ViagemDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados ao adicionar viagem");
        } catch (Exception e) {
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    public List<ViagemDTO> listarViagens() throws RegraDeNegocioException {
        try {
            return viagemRepository.listar().stream()
                    .map(pessoa -> objectMapper.convertValue(pessoa, ViagemDTO.class))
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados ao listar viagem" + e.getMessage());
        }
    }

    public ViagemDTO finalizarViagem(Integer id) throws RegraDeNegocioException { //Precisa pegar o id co caminhão que esta ligado nessa viagem
        try {
            ViagemEntity viagemRecuperada = getViagem(id);

            if(viagemRecuperada.isFinalizada() == true){
                throw new RegraDeNegocioException("Não é possivel finalizar uma viagem que já esta finalizada.");
            }

            viagemRecuperada.setFinalizada(true);
            ViagemEntity viagemEditada = viagemRepository.finalizarViagem(viagemRecuperada.getIdViagem(), viagemRecuperada);

            CaminhaoEntity caminhaoRecuperado = caminhaoService.getCaminhao(viagemRecuperada.getCaminhao().getIdCaminhao());
            caminhaoRecuperado.setEmViagem(StatusCaminhao.ESTACIONADO);
            caminhaoService.editar(caminhaoRecuperado.getIdCaminhao(), objectMapper.convertValue(caminhaoRecuperado, CaminhaoCreateDTO.class));

            return objectMapper.convertValue(viagemEditada, ViagemDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco de dados ao finalizar viagem" + e.getMessage());
        }catch (Exception e){
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    public List<ViagemDTO> listarViagensFinalizadas() throws RegraDeNegocioException {
        try {
            List<ViagemEntity> listar = viagemRepository.listar();
            List<ViagemEntity> viagensFinalizadas = listar.stream()
                    .filter(elemento -> elemento.isFinalizada() == true)
                    .toList();
            if (viagensFinalizadas.size() <= 0){
                throw new RegraDeNegocioException("O sistema não possui nem uma viagem finalizada no momento.");
            }
            return viagensFinalizadas.stream()
                    .map(pessoa -> objectMapper.convertValue(pessoa, ViagemDTO.class))
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }catch (Exception e){
            throw new RegraDeNegocioException(e.getMessage());
        }
        return null;
    }

    public ViagemDTO listarPorId(int id){
        try {
            ViagemEntity viagemRecuperada = getViagem(id);
            return objectMapper.convertValue(viagemRecuperada, ViagemDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ViagemDTO editarViagem(Integer id, ViagemCreateDTO viagem) {
        try {
            ViagemEntity viagemRecuperada = getViagem(id);

            viagemRecuperada.setIdViagem(id);
            viagemRecuperada.setIdCaminhao(viagem.getIdCaminhao());
            viagemRecuperada.setIdRota(viagem.getIdRota());
            viagemRecuperada.setIdUsuario(viagem.getIdUsuario());
            viagemRepository.editar(id, viagemRecuperada);

            CaminhaoEntity caminhaoRecuprado1 = caminhaoService.getCaminhao(viagemRecuperada.getCaminhao().getIdCaminhao());//Não esta mudando o status do caminhão para estacionado
            caminhaoRecuprado1.setEmViagem(StatusCaminhao.ESTACIONADO);
            caminhaoService.editar(caminhaoRecuprado1.getIdCaminhao(), objectMapper.convertValue(caminhaoRecuprado1, CaminhaoCreateDTO.class));

            CaminhaoEntity caminhaoRecuprado2 = caminhaoService.getCaminhao(viagem.getIdCaminhao());
            caminhaoRecuprado2.setEmViagem(StatusCaminhao.EM_VIAGEM);
            caminhaoService.editar(caminhaoRecuprado2.getIdCaminhao(), objectMapper.convertValue(caminhaoRecuprado2, CaminhaoCreateDTO.class));

            return objectMapper.convertValue(viagemRecuperada, ViagemDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ViagemEntity getViagem(Integer id) throws RegraDeNegocioException, BancoDeDadosException {
        return viagemRepository.listar().stream()
                .filter(u -> u.getIdViagem() == id)
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Viagem não encontrada"));
    }

}