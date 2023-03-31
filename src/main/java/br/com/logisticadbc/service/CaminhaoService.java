
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.out.CaminhaoDTO;
import br.com.logisticadbc.dto.out.LogDTO;
import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.entity.mongodb.LogEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.CaminhaoRepository;
import br.com.logisticadbc.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CaminhaoService {

    private final CaminhaoRepository caminhaoRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
    private final LogService logService;

    public CaminhaoDTO criar(Integer idUsuario, CaminhaoCreateDTO caminhaoCreateDTO)
            throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioService.buscarPorId(idUsuario);

        try {
            CaminhaoEntity caminhaoEntity = objectMapper.convertValue(caminhaoCreateDTO, CaminhaoEntity.class);
            caminhaoEntity.setStatus(StatusGeral.ATIVO);
            caminhaoEntity.setStatusCaminhao(StatusCaminhao.ESTACIONADO);
            caminhaoEntity.setUsuario(usuarioEntity);

            usuarioEntity.getCaminhoes().add(caminhaoEntity);

            LogEntity logEntity = getLog(usuarioEntity, "Operação de Criaçao de Caminhões",
                    TipoOperacao.CADASTRO);
            LogDTO logDTO = objectMapper.convertValue(logEntity, LogDTO.class);
            logService.save(logDTO);

            CaminhaoEntity caminhaoCriado = caminhaoRepository.save(caminhaoEntity);

            CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhaoCriado, CaminhaoDTO.class);
            caminhaoDTO.setIdUsuario(idUsuario);
            return caminhaoDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public CaminhaoDTO abastecer(Integer idCaminhao, Integer gasolina) throws RegraDeNegocioException {
        CaminhaoEntity caminhaoRecuperado = buscarPorId(idCaminhao);

        if (caminhaoRecuperado.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Caminhão inativo!");

        } else if (gasolina <= 0) {
            throw new RegraDeNegocioException("A gasolina informada não pode ser menor ou igual a 0");

        } else if (caminhaoRecuperado.getNivelCombustivel() + gasolina > 100) {
            throw new RegraDeNegocioException("Limite de gasolina excedido, por favor digite outro valor");
        }
        try {
            caminhaoRecuperado.setNivelCombustivel(caminhaoRecuperado.getNivelCombustivel() + gasolina);

            CaminhaoEntity caminhaoAbastecido = caminhaoRepository.save(caminhaoRecuperado);

            Integer idUsuario = caminhaoRecuperado.getUsuario().getIdUsuario();
            UsuarioEntity usuarioEntity = usuarioService.buscarPorId(idUsuario);
            LogEntity logEntity = getLog(usuarioEntity, "Operação de Abastecimento de Caminhões",
                    TipoOperacao.OUTROS);
            LogDTO logDTO = objectMapper.convertValue(logEntity, LogDTO.class);
            logService.save(logDTO);

            CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhaoAbastecido, CaminhaoDTO.class);
            caminhaoDTO.setIdUsuario(idUsuario);
            return caminhaoDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException(e.getMessage());
        }
    }

    public void deletar(Integer idCaminhao) throws RegraDeNegocioException {
        CaminhaoEntity caminhaoRecuperado = buscarPorId(idCaminhao);

        if (caminhaoRecuperado.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Caminhão já inativo!");
        }
        try {
            caminhaoRecuperado.setStatus(StatusGeral.INATIVO);
            caminhaoRepository.save(caminhaoRecuperado);
            Integer idUsuario = caminhaoRecuperado.getUsuario().getIdUsuario();
            UsuarioEntity usuarioEntity = usuarioService.buscarPorId(idUsuario);
            LogEntity logEntity = getLog(usuarioEntity, "Operação de Inativação de Caminhões",
                    TipoOperacao.EXCLUSAO);
            LogDTO logDTO = objectMapper.convertValue(logEntity, LogDTO.class);
            logService.save(logDTO);

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão");
        }
    }

    public List<CaminhaoDTO> listar() {
        return caminhaoRepository.findAll()
                .stream()
                .map(caminhao -> {
                    CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhao, CaminhaoDTO.class);
                    caminhaoDTO.setIdUsuario(caminhao.getUsuario().getIdUsuario());
                    return caminhaoDTO;
                })
                .toList();
    }

    public List<CaminhaoDTO> listarAtivos() {
        return caminhaoRepository.findAll()
                .stream()
                .filter(caminhao -> caminhao.getStatus().equals(StatusGeral.ATIVO))
                .map(caminhao -> {
                    CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhao, CaminhaoDTO.class);
                    caminhaoDTO.setIdUsuario(caminhao.getUsuario().getIdUsuario());
                    return caminhaoDTO;
                })
                .toList();
    }

    public List<CaminhaoDTO> listarInativos() {
        return caminhaoRepository.findAll()
                .stream()
                .filter(caminhao -> caminhao.getStatus().equals(StatusGeral.INATIVO))
                .map(caminhao -> {
                    CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhao, CaminhaoDTO.class);
                    caminhaoDTO.setIdUsuario(caminhao.getUsuario().getIdUsuario());
                    return caminhaoDTO;
                })
                .toList();
    }

    public CaminhaoDTO listarPorId(Integer idCaminhao) throws RegraDeNegocioException {
        CaminhaoEntity caminhaoRecuperado = buscarPorId(idCaminhao);
        try {
            CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhaoRecuperado, CaminhaoDTO.class);
            caminhaoDTO.setIdUsuario(caminhaoRecuperado.getUsuario().getIdUsuario());
            return caminhaoDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem");
        }
    }

    public List<CaminhaoDTO> listarCaminhoesLivres() {
        return caminhaoRepository
                .findByStatusCaminhaoEquals(StatusCaminhao.ESTACIONADO)
                .stream()
                .filter(caminhao -> caminhao.getStatus().equals(StatusGeral.ATIVO))
                .map(caminhao -> {
                    CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhao, CaminhaoDTO.class);
                    caminhaoDTO.setIdUsuario(caminhao.getUsuario().getIdUsuario());
                    return caminhaoDTO;
                })
                .toList();
    }

    public List<CaminhaoDTO> listarPorIdColaborador(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioService.buscarPorId(idUsuario);

        return usuarioEntity.getCaminhoes()
                .stream()
                .map(caminhao -> {
                    CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhao, CaminhaoDTO.class);
                    caminhaoDTO.setIdUsuario(idUsuario);
                    return caminhaoDTO;
                })
                .toList();
    }

    public CaminhaoEntity buscarPorId(Integer idCaminhao) throws RegraDeNegocioException {
        return caminhaoRepository.findById(idCaminhao)
                .orElseThrow(() -> new RegraDeNegocioException("Caminhao não encontrado"));
    }

    public void mudarStatus(CaminhaoEntity caminhao, StatusCaminhao status) {
        caminhao.setStatusCaminhao(status);
        caminhaoRepository.save(caminhao);
    }

    public LogEntity getLog(UsuarioEntity usuario, String descricao, TipoOperacao tipoOperacao) throws RegraDeNegocioException {
        Integer idUsuario = usuario.getIdUsuario();
        UsuarioEntity usuarioEntity = usuarioService.buscarPorId(idUsuario);

        LogEntity log = new LogEntity();
        log.setId(usuarioEntity.getIdUsuario().toString());
        log.setLoginOperador(usuarioEntity.getLogin());
        log.setDescricao(descricao);
        log.setTipoOperacao(tipoOperacao);

        return log;
    }
}
