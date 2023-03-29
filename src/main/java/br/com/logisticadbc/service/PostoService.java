package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.PostoCreateDTO;
import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.mongodb.PostoEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.PostoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public PostoDTO criar(PostoCreateDTO postoCreateDTO) throws RegraDeNegocioException {

        try {
            GeoJsonPoint locationPoint = new GeoJsonPoint(
                    Double.parseDouble(postoCreateDTO.getLongitude()),
                    Double.parseDouble(postoCreateDTO.getLatitude()));

            PostoEntity postoEntity = objectMapper.convertValue(postoCreateDTO, PostoEntity.class);
            postoEntity.setLocation(locationPoint);
            postoEntity.setStatus(StatusGeral.ATIVO);

            postoRepository.save(postoEntity);

            PostoDTO postoDTO = objectMapper.convertValue(postoEntity, PostoDTO.class);
            return postoDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    // TODO REVISAR
    public PostoDTO editar(String idPosto, PostoCreateDTO postoCreateDTO) throws RegraDeNegocioException {
        PostoEntity postoEncontrado = buscarPorId(idPosto);

        if (postoEncontrado.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Posto inativo!");
        }
        try {
            postoEncontrado.setNome(postoCreateDTO.getNome());
            postoEncontrado.setValorCombustivel(postoCreateDTO.getValorCombustivel());

            postoRepository.save(postoEncontrado);

            PostoDTO postoDTO = objectMapper.convertValue(postoEncontrado, PostoDTO.class);
            return postoDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    // TODO REVISAR
    public void deletar(String idPosto) throws RegraDeNegocioException {
        PostoEntity postoEncontrado = buscarPorId(idPosto);

        if (postoEncontrado.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Posto já inativo!");
        }
        try {
            postoEncontrado.setStatus(StatusGeral.INATIVO);
            postoRepository.save(postoEncontrado);

        } catch (Exception e) {
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

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<PostoDTO> listarPostosAtivos() {
        return postoRepository
                .findByStatusEquals(StatusGeral.ATIVO)
                .stream()
                .map(posto -> {
                    PostoDTO postoDTO = objectMapper.convertValue(posto, PostoDTO.class);
                    return postoDTO;
                })
                .toList();
    }

    public List<PostoDTO> listarPostosInativos() {return postoRepository
            .findByStatusEquals(StatusGeral.INATIVO)
            .stream()
            .map(posto -> {
                PostoDTO postoDTO = objectMapper.convertValue(posto, PostoDTO.class);
                return postoDTO;
            })
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

    public List<PostoDTO> listByCidade (String cidade) {
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