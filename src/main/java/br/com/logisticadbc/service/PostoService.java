package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.PostoCreateDTO;
import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.PostoEntity;
import br.com.logisticadbc.entity.enums.StatusUsuario;
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
    private final ColaboradorService colaboradorService;
    private final ObjectMapper objectMapper;

    public PostoDTO criar(Integer idUsuario, PostoCreateDTO postoCreateDTO) throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEntity = colaboradorService.buscarPorId(idUsuario);

            if (colaboradorEntity.getStatusUsuario().equals(StatusUsuario.INATIVO)) {
                throw new RegraDeNegocioException("Usuário inativo!");
            }

            PostoEntity postoEntity = objectMapper.convertValue(postoCreateDTO, PostoEntity.class);
            postoEntity.setColaborador(colaboradorEntity);

            colaboradorEntity.getPostos().add(postoEntity);

            postoRepository.save(postoEntity);

            PostoDTO postoDTO = objectMapper.convertValue(postoEntity, PostoDTO.class);
            postoDTO.setIdUsuario(colaboradorEntity.getIdUsuario());
            return postoDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public PostoDTO editar(Integer idPosto, PostoCreateDTO postoCreateDTO) throws RegraDeNegocioException {
        try {
            PostoEntity postoEntity = buscarPorId(idPosto);

            postoEntity.setNome(postoCreateDTO.getNome());
            postoEntity.setValorCombustivel(postoCreateDTO.getValorCombustivel());

            ColaboradorEntity colaboradorEntity =
                    colaboradorService.buscarPorId(postoEntity.getColaborador().getIdUsuario());
            colaboradorEntity.getPostos().add(postoEntity);

            postoRepository.save(postoEntity);

            PostoDTO postoDTO = objectMapper.convertValue(postoEntity, PostoDTO.class);
            postoDTO.setIdUsuario(colaboradorEntity.getIdUsuario());
            return postoDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletar(Integer idPosto) throws RegraDeNegocioException {
        try {
            PostoEntity postoEntity = buscarPorId(idPosto);

            postoRepository.deleteById(postoEntity.getIdPosto());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }

    public List<PostoDTO> listar() {
        return postoRepository
                .findAll()
                .stream()
                .map(posto -> {
                    PostoDTO postoDTO = objectMapper.convertValue(posto, PostoDTO.class);
                    postoDTO.setIdUsuario(posto.getColaborador().getIdUsuario());
                    return postoDTO;
                })
                .toList();
    }

    public PostoDTO listarPorId(Integer idPosto) throws RegraDeNegocioException {
        try {
            PostoEntity postoRecuperado = buscarPorId(idPosto);

            PostoDTO postoDTO = objectMapper.convertValue(postoRecuperado, PostoDTO.class);
            postoDTO.setIdUsuario(postoRecuperado.getColaborador().getIdUsuario());
            postoDTO.setIdPosto(idPosto);
            return postoDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    private PostoEntity buscarPorId(Integer idPosto) throws RegraDeNegocioException {
        return postoRepository.findById(idPosto)
                .orElseThrow(() -> new RegraDeNegocioException("Posto não encontrado"));
    }
}