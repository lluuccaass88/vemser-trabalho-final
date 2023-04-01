package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.PostoCreateDTO;
import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.dto.out.UsuarioDTO;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.entity.mongodb.PostoEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.PostoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostoService {

    private final PostoRepository postoRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final LogService logService;

    public PostoDTO criar(PostoCreateDTO postoCreateDTO) throws RegraDeNegocioException {
        UsuarioDTO loggedUser = usuarioService.getLoggedUser();

        try {
            GeoJsonPoint locationPoint = new GeoJsonPoint(Double.parseDouble(postoCreateDTO.getLongitude()),
                    Double.parseDouble(postoCreateDTO.getLongitude()));

            PostoEntity postoEntity = objectMapper.convertValue(postoCreateDTO, PostoEntity.class);
            postoEntity.setLocation(locationPoint);
            postoEntity.setStatus(StatusGeral.ATIVO);

            PostoEntity postoCriado = postoRepository.save(postoEntity);

            String descricao = "Operação de Cadastro de Rota | " + postoCriado.getNome();
            logService.gerarLog(loggedUser.getLogin(), descricao, TipoOperacao.CADASTRO);

            PostoDTO postoDTO = objectMapper.convertValue(postoCriado, PostoDTO.class);
            return postoDTO;

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public PostoDTO editar(String idPosto, PostoCreateDTO postoCreateDTO) throws RegraDeNegocioException {
        PostoEntity postoEncontrado = buscarPorId(idPosto);
        UsuarioDTO loggedUser = usuarioService.getLoggedUser();

        if (postoEncontrado.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Posto inativo!");
        }
        try {
            GeoJsonPoint locationPoint = new GeoJsonPoint(Double.parseDouble(postoCreateDTO.getLongitude()),
                    Double.parseDouble(postoCreateDTO.getLongitude()));

            postoEncontrado.setNome(postoCreateDTO.getNome());
            postoEncontrado.setValorCombustivel(postoCreateDTO.getValorCombustivel());
            postoEncontrado.setCidade(postoCreateDTO.getCidade());
            postoEncontrado.setLocation(locationPoint);

            PostoEntity postoEditado = postoRepository.save(postoEncontrado);

            String descricao = "Operação de Cadastro de Rota | " + postoEncontrado.getNome();
            logService.gerarLog(loggedUser.getLogin(), descricao, TipoOperacao.ALTERACAO);

            PostoDTO postoDTO = objectMapper.convertValue(postoEditado, PostoDTO.class);
            return postoDTO;

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletar(String idPosto) throws RegraDeNegocioException {
        PostoEntity postoEncontrado = buscarPorId(idPosto);
        UsuarioDTO loggedUser = usuarioService.getLoggedUser();

        if (postoEncontrado.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Posto já inativo!");
        }
        try {
            postoEncontrado.setStatus(StatusGeral.INATIVO);
            postoRepository.save(postoEncontrado);

            String descricao = "Operação de Cadastro de Rota | " + postoEncontrado.getNome();
            logService.gerarLog(loggedUser.getLogin(), descricao, TipoOperacao.EXCLUSAO);

        } catch (DataAccessException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }

    public List<PostoDTO> listar() {
        return postoRepository.findAll()
                .stream()
                .map(posto -> {
                    PostoDTO postoDTO = objectMapper.convertValue(posto, PostoDTO.class);
                    return postoDTO;
                })
                .toList();
    }

    public PostoDTO listarPorId(String idPosto) throws RegraDeNegocioException {
        PostoEntity postoRecuperado = buscarPorId(idPosto);
        try {
            PostoDTO postoDTO = objectMapper.convertValue(postoRecuperado, PostoDTO.class);
            postoDTO.setId(idPosto);
            return postoDTO;

        } catch (ObjectNotFoundException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<PostoDTO> listarPostosAtivos() {
        return postoRepository
                .findByStatusEquals(StatusGeral.ATIVO)
                .stream()
                .map(posto -> {
                    PostoDTO postoDTO = objectMapper.convertValue(posto, PostoDTO.class);
                    return postoDTO;})
                .toList();
    }

    public List<PostoDTO> listarPostosInativos() {
        return postoRepository
                .findByStatusEquals(StatusGeral.INATIVO)
                .stream()
                .map(posto -> {
                    PostoDTO postoDTO = objectMapper.convertValue(posto, PostoDTO.class);
                    return postoDTO;})
                .toList();
    }

    public List<PostoDTO> listarPorLocalizacao(String longitude, String latitude, Double distancia) {
        List<PostoEntity> postos = postoRepository.findByLocationNear(
                new Point(Double.parseDouble(longitude), Double.parseDouble(latitude)),
                new Distance(distancia, Metrics.KILOMETERS));

        return postos
                .stream()
                .map(posto -> objectMapper.convertValue(posto, PostoDTO.class))
                .toList();
    }

    public List<PostoDTO> listByCidade(String cidade) {
        List<PostoEntity> postos = postoRepository.findByCidadeIgnoreCase(cidade);

        return postos
                .stream()
                .map(posto -> objectMapper.convertValue(posto, PostoDTO.class))
                .toList();
    }

    public PostoEntity buscarPorId(String idPosto) throws RegraDeNegocioException {
        return postoRepository.findById(idPosto)
                .orElseThrow(() -> new RegraDeNegocioException("Posto não encontrado"));
    }
}