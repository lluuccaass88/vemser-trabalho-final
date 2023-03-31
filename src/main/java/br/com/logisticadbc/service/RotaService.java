package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.LogDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.entity.mongodb.LogEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.RotaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RotaService {

    private final RotaRepository rotaRepository;
    private final UsuarioService usuarioService;
    //    private final PostoService postoService;
    private final ObjectMapper objectMapper;
    private final LogService logService;


    public RotaDTO criar(Integer idUsuario, RotaCreateDTO rotaCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = usuarioService.buscarPorId(idUsuario);

        try {
            RotaEntity rotaEntity = objectMapper.convertValue(rotaCreateDTO, RotaEntity.class);
            rotaEntity.setStatus(StatusGeral.ATIVO);
            rotaEntity.setUsuario(usuarioEncontrado); // Atribui idusuario a rota criada

            usuarioEncontrado.getRotas().add(rotaEntity); // Atribui rota criada ao Colaborador //TODO DESCOBRIR COMO TESTA A RELAÇÃO

            logService.gerarLog(usuarioEncontrado, "Operação de Cadastro de Rotas",
                    TipoOperacao.CADASTRO);
            rotaRepository.save(rotaEntity);

            RotaDTO rotaDTO = objectMapper.convertValue(rotaEntity, RotaDTO.class);
            rotaDTO.setIdUsuario(idUsuario);
            return rotaDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public RotaDTO editar(Integer idRota, RotaCreateDTO rotaCreateDTO) throws RegraDeNegocioException {
        RotaEntity rotaEncontrada = buscarPorId(idRota);

        if (rotaEncontrada.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Rota inativa!");
        }
        try {
            rotaEncontrada.setDescricao(rotaCreateDTO.getDescricao());
            rotaEncontrada.setLocalPartida(rotaCreateDTO.getLocalPartida());
            rotaEncontrada.setLocalDestino(rotaCreateDTO.getLocalDestino());

            UsuarioEntity usuarioEncontrado = usuarioService.buscarPorId(
                    rotaEncontrada.getUsuario().getIdUsuario());
            usuarioEncontrado.getRotas().add(rotaEncontrada);

            logService.gerarLog(usuarioEncontrado, "Operação de Alteração de Rotas",
                    TipoOperacao.ALTERACAO);

            rotaRepository.save(rotaEncontrada);
            RotaDTO rotaDTO = objectMapper.convertValue(rotaEncontrada, RotaDTO.class);
            rotaDTO.setIdUsuario(usuarioEncontrado.getIdUsuario());
            return rotaDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletar(Integer idRota) throws RegraDeNegocioException {
        RotaEntity rotaEncontrada = buscarPorId(idRota);
        if (rotaEncontrada.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Rota já inativa!");
        }
        try {
            rotaEncontrada.setStatus(StatusGeral.INATIVO);
            rotaRepository.save(rotaEncontrada);
            UsuarioEntity usuarioEncontrado = usuarioService.buscarPorId(
                    rotaEncontrada.getUsuario().getIdUsuario());
            usuarioEncontrado.getRotas().add(rotaEncontrada);

            logService.gerarLog(usuarioEncontrado, "Operação de Inativação de Rotas",
                    TipoOperacao.EXCLUSAO);

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }

    public List<RotaDTO> listar() {
        List<RotaDTO> rotasDTO = rotaRepository.findAll()
                .stream()
                .map(rota -> {
                    RotaDTO rotaDTO = objectMapper.convertValue(rota, RotaDTO.class);
                    rotaDTO.setIdUsuario(rota.getUsuario().getIdUsuario());
                    return rotaDTO;
                })
                .toList();
        return rotasDTO;
    }

    public RotaDTO listarPorId(Integer idRota) throws RegraDeNegocioException {
        RotaEntity rotaRecuperado = buscarPorId(idRota);

        try {
            RotaDTO rotaDTO = objectMapper.convertValue(rotaRecuperado, RotaDTO.class);
            rotaDTO.setIdUsuario(rotaRecuperado.getUsuario().getIdUsuario());
            return rotaDTO;
        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<RotaDTO> listarPorLocalPartida(String localPartida) throws RegraDeNegocioException {
        try {
            List<RotaEntity> rotasPartida = rotaRepository.findBylocalPartidaIgnoreCase(localPartida);
            if (rotasPartida.size() == 0) {
                throw new RegraDeNegocioException("Local de partida não encontrado.");
            }
            return rotasPartida
                    .stream()
                    .map(rota -> {
                        RotaDTO rotaDTO = objectMapper.convertValue(rota, RotaDTO.class);
                        rotaDTO.setIdUsuario(rota.getUsuario().getIdUsuario());
                        return rotaDTO;
                    })
                    .toList();
        } catch (Exception e) {
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    public List<RotaDTO> listarPorLocalDestino(String localDestino) throws RegraDeNegocioException {
        try {
            List<RotaEntity> rotasDestino = rotaRepository.findBylocalDestinoIgnoreCase(localDestino);
            if (rotasDestino.size() == 0) {
                throw new RegraDeNegocioException("Local de destino não encontrado.");
            }
            return rotasDestino
                    .stream()
                    .map(rota -> {
                        RotaDTO rotaDTO = objectMapper.convertValue(rota, RotaDTO.class);
                        rotaDTO.setIdUsuario(rota.getUsuario().getIdUsuario());
                        return rotaDTO;
                    })
                    .toList();
        } catch (Exception e) {
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    public List<RotaDTO> listarRotasAtivas() {
        return rotaRepository
                .findByStatusEquals(StatusGeral.ATIVO)
                .stream()
                .map(rota -> {
                    RotaDTO rotaDTO = objectMapper.convertValue(rota, RotaDTO.class);
                    rotaDTO.setIdUsuario(rota.getUsuario().getIdUsuario());
                    return rotaDTO;
                })
                .toList();
    }

    public List<RotaDTO> listarRotasInativas() {
        return rotaRepository
                .findByStatusEquals(StatusGeral.INATIVO)
                .stream()
                .map(rota -> {
                    RotaDTO rotaDTO = objectMapper.convertValue(rota, RotaDTO.class);
                    rotaDTO.setIdUsuario(rota.getUsuario().getIdUsuario());
                    return rotaDTO;
                })
                .toList();
    }

    public List<RotaDTO> listarPorIdColaborador(Integer idColaborador) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = usuarioService.buscarPorId(idColaborador);

        return usuarioEncontrado.getRotas()
                .stream()
                .map(rota -> {
                    RotaDTO rotaDTO = objectMapper.convertValue(rota, RotaDTO.class);
                    rotaDTO.setIdUsuario(idColaborador);
                    return rotaDTO;
                })
                .toList();
    }

    public RotaEntity buscarPorId(Integer idRota) throws RegraDeNegocioException {
        return rotaRepository.findById(idRota)
                .orElseThrow(() -> new RegraDeNegocioException("Rota não encontrada"));
    }

}