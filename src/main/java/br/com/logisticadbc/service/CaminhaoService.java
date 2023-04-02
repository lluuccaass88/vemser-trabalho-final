
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.out.CaminhaoDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.CaminhaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
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
        UsuarioDTO loggedUser = usuarioService.getLoggedUser();

        try {
            CaminhaoEntity caminhaoEntity = objectMapper.convertValue(caminhaoCreateDTO, CaminhaoEntity.class);
            caminhaoEntity.setStatus(StatusGeral.ATIVO);
            caminhaoEntity.setStatusCaminhao(StatusCaminhao.ESTACIONADO);
            caminhaoEntity.setUsuario(usuarioEntity);

            usuarioEntity.getCaminhoes().add(caminhaoEntity);

            CaminhaoEntity caminhaoCriado = caminhaoRepository.save(caminhaoEntity);

            String descricao = "Operação em Caminhão: " +
                    caminhaoEntity.getModelo() + " | " + caminhaoEntity.getPlaca();

            logService.gerarLog(loggedUser.getLogin(), descricao, TipoOperacao.CADASTRO);

            CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhaoCriado, CaminhaoDTO.class);
            caminhaoDTO.setIdUsuario(idUsuario);
            return caminhaoDTO;

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
        }
    }

    public CaminhaoDTO abastecer(Integer idCaminhao, Integer gasolina) throws RegraDeNegocioException {
        CaminhaoEntity caminhaoRecuperado = buscarPorId(idCaminhao);
        UsuarioDTO loggedUser = usuarioService.getLoggedUser();

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

            String descricao = "Operação em Caminhão: " +
                    caminhaoRecuperado.getModelo() + " | " + caminhaoRecuperado.getPlaca();
            logService.gerarLog(loggedUser.getLogin(), descricao, TipoOperacao.ALTERACAO);

            CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhaoAbastecido, CaminhaoDTO.class);
            caminhaoDTO.setIdUsuario(caminhaoAbastecido.getUsuario().getIdUsuario());
            return caminhaoDTO;

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
        }
    }

    public void deletar(Integer idCaminhao) throws RegraDeNegocioException {
        CaminhaoEntity caminhaoRecuperado = buscarPorId(idCaminhao);
        UsuarioDTO loggedUser = usuarioService.getLoggedUser();

        if (caminhaoRecuperado.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Caminhão já inativo!");
        }
        try {
            caminhaoRecuperado.setStatus(StatusGeral.INATIVO);
            caminhaoRepository.save(caminhaoRecuperado);

            String descricao = "Operação em Caminhão: " +
                    caminhaoRecuperado.getModelo() + " | " + caminhaoRecuperado.getPlaca();
            logService.gerarLog(loggedUser.getLogin(), descricao, TipoOperacao.EXCLUSAO);

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Erro ao salvar no banco.");
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
            throw new RegraDeNegocioException("Erro de conversão.");
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
}
