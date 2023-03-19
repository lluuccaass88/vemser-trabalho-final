
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.CaminhaoCreateDTO;
import br.com.logisticadbc.dto.out.CaminhaoDTO;
import br.com.logisticadbc.entity.CaminhaoEntity;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.enums.StatusCaminhao;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.CaminhaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CaminhaoService {

    private final CaminhaoRepository caminhaoRepository;
    private final ColaboradorService colaboradorService;
    private final ObjectMapper objectMapper;

    public CaminhaoDTO criar(Integer idUsuario, CaminhaoCreateDTO caminhaoCreateDTO)
            throws RegraDeNegocioException {
        ColaboradorEntity colaboradorEntity = colaboradorService.buscarPorId(idUsuario);

        try {
            CaminhaoEntity caminhaoEntity = objectMapper.convertValue(caminhaoCreateDTO, CaminhaoEntity.class);
            caminhaoEntity.setStatus(StatusGeral.ATIVO);
            caminhaoEntity.setStatusCaminhao(StatusCaminhao.ESTACIONADO);
            caminhaoEntity.setColaborador(colaboradorEntity);

            colaboradorEntity.getCaminhoes().add(caminhaoEntity);

            caminhaoRepository.save(caminhaoEntity);

            CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhaoEntity, CaminhaoDTO.class);
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

            ColaboradorEntity colaboradorEntity =
                    colaboradorService.buscarPorId(caminhaoRecuperado.getColaborador().getIdUsuario());

            caminhaoRepository.save(caminhaoRecuperado);

            CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhaoRecuperado, CaminhaoDTO.class);
            caminhaoDTO.setIdUsuario(colaboradorEntity.getIdUsuario());
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

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão");
        }
    }

    public List<CaminhaoDTO> listar() {
        return caminhaoRepository.findAll()
                .stream()
                .map(caminhao -> {
                    CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhao, CaminhaoDTO.class);
                    caminhaoDTO.setIdUsuario(caminhao.getColaborador().getIdUsuario());
                    return caminhaoDTO;})
                .toList();
    }

    public List<CaminhaoDTO> listarAtivos() {
        return caminhaoRepository.findAll()
                .stream()
                .filter(caminhao -> caminhao.getStatus().equals(StatusGeral.ATIVO))
                .map(caminhao -> {
                    CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhao, CaminhaoDTO.class);
                    caminhaoDTO.setIdUsuario(caminhao.getColaborador().getIdUsuario());
                    return caminhaoDTO;})
                .toList();
    }

    public List<CaminhaoDTO> listarInativos() {
        return caminhaoRepository.findAll()
                .stream()
                .filter(caminhao -> caminhao.getStatus().equals(StatusGeral.INATIVO))
                .map(caminhao -> {
                    CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhao, CaminhaoDTO.class);
                    caminhaoDTO.setIdUsuario(caminhao.getColaborador().getIdUsuario());
                    return caminhaoDTO;})
                .toList();
    }

    public CaminhaoDTO listarPorId(Integer idCaminhao) throws RegraDeNegocioException {
        CaminhaoEntity caminhaoRecuperado = buscarPorId(idCaminhao);
        try {
            CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhaoRecuperado, CaminhaoDTO.class);
            caminhaoDTO.setIdUsuario(caminhaoRecuperado.getColaborador().getIdUsuario());
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
                    caminhaoDTO.setIdUsuario(caminhao.getColaborador().getIdUsuario());
                    return caminhaoDTO;})
                .toList();
    }

    public List<CaminhaoDTO> listarPorIdColaborador(Integer idColaborador) throws RegraDeNegocioException {
        ColaboradorEntity colaboradorEncontrado = colaboradorService.buscarPorId(idColaborador);

        return colaboradorEncontrado.getCaminhoes()
                .stream()
                .map(caminhao -> {
                    CaminhaoDTO caminhaoDTO = objectMapper.convertValue(caminhao, CaminhaoDTO.class);
                    caminhaoDTO.setIdUsuario(idColaborador);
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
