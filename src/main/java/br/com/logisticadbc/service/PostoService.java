package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.PostoCreateDTO;
import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.entity.PostoEntity;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.PostoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostoService {

    private final PostoRepository postoRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public PostoDTO criar(Integer idUsuario, PostoCreateDTO postoCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioService.buscarPorId(idUsuario);

        try {
            PostoEntity postoEntity = objectMapper.convertValue(postoCreateDTO, PostoEntity.class);
            postoEntity.setStatus(StatusGeral.ATIVO);
            postoEntity.setUsuario(usuarioEntity);

            usuarioEntity.getPostos().add(postoEntity);

            postoRepository.save(postoEntity);

            PostoDTO postoDTO = objectMapper.convertValue(postoEntity, PostoDTO.class);
            postoDTO.setIdUsuario(idUsuario);
            return postoDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public PostoDTO editar(Integer idPosto, PostoCreateDTO postoCreateDTO) throws RegraDeNegocioException {
        PostoEntity postoEncontrado = buscarPorId(idPosto);

        if (postoEncontrado.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Posto inativo!");
        }
        try {
            postoEncontrado.setNome(postoCreateDTO.getNome());
            postoEncontrado.setValorCombustivel(postoCreateDTO.getValorCombustivel());

            UsuarioEntity usuarioEntity =
                    usuarioService.buscarPorId(postoEncontrado.getUsuario().getIdUsuario());

            usuarioEntity.getPostos().add(postoEncontrado);

            postoRepository.save(postoEncontrado);

            PostoDTO postoDTO = objectMapper.convertValue(postoEncontrado, PostoDTO.class);
            postoDTO.setIdUsuario(usuarioEntity.getIdUsuario());
            return postoDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletar(Integer idPosto) throws RegraDeNegocioException {
        PostoEntity postoEncontrado = buscarPorId(idPosto);

        if (postoEncontrado.getStatus().equals(StatusGeral.INATIVO)) {
            throw new RegraDeNegocioException("Posto já inativo!");
        }
        try {
            postoEncontrado.setStatus(StatusGeral.INATIVO);
            postoRepository.save(postoEncontrado);

            UsuarioEntity usuarioEntity =
                    usuarioService.buscarPorId(postoEncontrado.getUsuario().getIdUsuario());

            usuarioEntity.getPostos().add(postoEncontrado);

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }

    public List<PostoDTO> listar() {
        return postoRepository.findAll()
                .stream()
                .map(posto -> {
                    PostoDTO postoDTO = objectMapper.convertValue(posto, PostoDTO.class);
                    postoDTO.setIdUsuario(posto.getUsuario().getIdUsuario());
                    return postoDTO;
                })
                .toList();
    }

    public PostoDTO listarPorId(Integer idPosto) throws RegraDeNegocioException {
        PostoEntity postoRecuperado = buscarPorId(idPosto);

        try {
            PostoDTO postoDTO = objectMapper.convertValue(postoRecuperado, PostoDTO.class);
            postoDTO.setIdUsuario(postoRecuperado.getUsuario().getIdUsuario());
            postoDTO.setIdPosto(idPosto);
            return postoDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<PostoDTO> listarPorIdColaborador(Integer idColaborador) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = usuarioService.buscarPorId(idColaborador);

        return usuarioEncontrado.getPostos()
                .stream()
                .map(posto -> {
                    PostoDTO postoDTO = objectMapper.convertValue(posto, PostoDTO.class);
                    postoDTO.setIdUsuario(idColaborador);
                    return postoDTO;
                })
                .toList();
    }

    public List<PostoDTO> listarPostosAtivos() {
        return postoRepository
                .findByStatusEquals(StatusGeral.ATIVO)
                .stream()
                .map(posto -> {
                    PostoDTO postoDTO = objectMapper.convertValue(posto, PostoDTO.class);
                    postoDTO.setIdUsuario(posto.getUsuario().getIdUsuario());
                    return postoDTO;
                })
                .toList();
    }

    public List<PostoDTO> listarPostosInativos() {return postoRepository
            .findByStatusEquals(StatusGeral.INATIVO)
            .stream()
            .map(posto -> {
                PostoDTO postoDTO = objectMapper.convertValue(posto, PostoDTO.class);
                postoDTO.setIdUsuario(posto.getUsuario().getIdUsuario());
                return postoDTO;
            })
            .toList();
    }

    public PostoEntity buscarPorId(Integer idPosto) throws RegraDeNegocioException {
        return postoRepository.findById(idPosto)
                .orElseThrow(() -> new RegraDeNegocioException("Posto não encontrado"));
    }
}