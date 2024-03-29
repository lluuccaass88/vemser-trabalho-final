package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.RotaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RotaService {

    private final RotaRepository rotaRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
    private final LogService logService;


    public RotaDTO criar(Integer idUsuario, RotaCreateDTO rotaCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = usuarioService.buscarPorId(idUsuario);
        UsuarioDTO loggedUser = usuarioService.getLoggedUser();

        try {
            RotaEntity rotaEntity = objectMapper.convertValue(rotaCreateDTO, RotaEntity.class);
            rotaEntity.setStatus(StatusGeral.ATIVO);
            rotaEntity.setUsuario(usuarioEncontrado);

            usuarioEncontrado.getRotas().add(rotaEntity);

            rotaRepository.save(rotaEntity);

            String descricao = "Operação em Rota: " + rotaEntity.getDescricao();
            logService.gerarLog(loggedUser.getLogin(), descricao, TipoOperacao.CADASTRO);

            RotaDTO rotaDTO = objectMapper.convertValue(rotaEntity, RotaDTO.class);
            rotaDTO.setIdUsuario(idUsuario);
            return rotaDTO;

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
        }
    }

    public RotaDTO editar(Integer idRota, RotaCreateDTO rotaCreateDTO) throws RegraDeNegocioException {
        RotaEntity rotaEncontrada = buscarPorId(idRota);
        UsuarioDTO loggedUser = usuarioService.getLoggedUser();

        if (rotaEncontrada.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Rota inativa!");
        }
        try {
            rotaEncontrada.setDescricao(rotaCreateDTO.getDescricao());
            rotaEncontrada.setLocalPartida(rotaCreateDTO.getLocalPartida());
            rotaEncontrada.setLocalDestino(rotaCreateDTO.getLocalDestino());

            UsuarioEntity usuarioEncontrado = usuarioService.buscarPorId(rotaEncontrada.getUsuario().getIdUsuario());
            usuarioEncontrado.getRotas().add(rotaEncontrada);

            rotaRepository.save(rotaEncontrada);

            String descricao = "Operação em Rota: " + rotaEncontrada.getDescricao();
            logService.gerarLog(loggedUser.getLogin(), descricao, TipoOperacao.ALTERACAO);

            RotaDTO rotaDTO = objectMapper.convertValue(rotaEncontrada, RotaDTO.class);
            rotaDTO.setIdUsuario(usuarioEncontrado.getIdUsuario());
            return rotaDTO;

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
        }
    }

    public void deletar(Integer idRota) throws RegraDeNegocioException {
        RotaEntity rotaEncontrada = buscarPorId(idRota);

        UsuarioDTO loggedUser = usuarioService.getLoggedUser();

        if (rotaEncontrada.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Rota já inativa!");
        }
        try {
            rotaEncontrada.setStatus(StatusGeral.INATIVO);
            rotaRepository.save(rotaEncontrada);

            UsuarioEntity usuarioEncontrado = usuarioService.buscarPorId(
                    rotaEncontrada.getUsuario().getIdUsuario());
            usuarioEncontrado.getRotas().add(rotaEncontrada);

            String descricao = "Operação em Rota: " + rotaEncontrada.getDescricao();

            logService.gerarLog(loggedUser.getLogin(), descricao, TipoOperacao.EXCLUSAO);

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
        }
    }

    public List<RotaDTO> listar() {
        return rotaRepository.findAll()
                .stream()
                .map(rota -> {
                    RotaDTO rotaDTO = objectMapper.convertValue(rota, RotaDTO.class);
                    rotaDTO.setIdUsuario(rota.getUsuario().getIdUsuario());
                    return rotaDTO;
                })
                .toList();
    }

    public RotaDTO listarPorId(Integer idRota) throws RegraDeNegocioException {
        RotaEntity rotaRecuperado = buscarPorId(idRota);

        try {
            RotaDTO rotaDTO = objectMapper.convertValue(rotaRecuperado, RotaDTO.class);
            rotaDTO.setIdUsuario(rotaRecuperado.getUsuario().getIdUsuario());
            return rotaDTO;
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro de conversão.");
        }
    }

    public List<RotaDTO> listarPorLocalPartida(String localPartida) throws RegraDeNegocioException {
        List<RotaEntity> rotasPartida = rotaRepository.findBylocalPartidaIgnoreCase(localPartida);

        if (rotasPartida.size() == 0) {
            throw new RegraDeNegocioException("Local de partida não encontrado.");
        }
        List<RotaDTO> rotaDTOS = rotasPartida
                .stream()
                .map(rota -> {
                    RotaDTO rotaDTO = objectMapper.convertValue(rota, RotaDTO.class);
                    rotaDTO.setIdUsuario(rota.getUsuario().getIdUsuario());
                    return rotaDTO;
                })
                .toList();
        return rotaDTOS;
    }

    public List<RotaDTO> listarPorLocalDestino(String localDestino) throws RegraDeNegocioException {
        List<RotaEntity> rotasDestino = rotaRepository.findBylocalDestinoIgnoreCase(localDestino);

        if (rotasDestino.size() == 0) {
            throw new RegraDeNegocioException("Local de destino não encontrado.");
        }
        List<RotaDTO> rotaDTOS = rotasDestino
                .stream()
                .map(rota -> {
                    RotaDTO rotaDTO = objectMapper.convertValue(rota, RotaDTO.class);
                    rotaDTO.setIdUsuario(rota.getUsuario().getIdUsuario());
                    return rotaDTO;
                })
                .toList();
        return rotaDTOS;
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