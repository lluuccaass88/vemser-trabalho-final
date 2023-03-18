package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.PostoDTO;
import br.com.logisticadbc.dto.out.RotaComPostosDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.PostoEntity;
import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.RotaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RotaService {

    // TODO fazer metodo de cadastrar posto em rota

    private final RotaRepository rotaRepository;
    private final ColaboradorService colaboradorService;
    private final PostoService postoService;
    private final ObjectMapper objectMapper;

    public RotaDTO criar(Integer idUsuario, RotaCreateDTO rotaCreateDTO) throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEncontrado = colaboradorService.buscarPorId(idUsuario);

            if (colaboradorEncontrado.getStatus().equals(StatusGeral.INATIVO)) {
                throw new RegraDeNegocioException("Usuário inativo!");
            }

            RotaEntity rotaEntity = objectMapper.convertValue(rotaCreateDTO, RotaEntity.class);
            rotaEntity.setColaborador(colaboradorEncontrado); // Atribui idusuario a rota criada
            colaboradorEncontrado.getRotas().add(rotaEntity); // Atribui rota criada ao Colaborador

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
        try {
            RotaEntity rotaEncontrada = buscarPorId(idRota);
            rotaEncontrada.setDescricao(rotaCreateDTO.getDescricao());
            rotaEncontrada.setLocalPartida(rotaCreateDTO.getLocalPartida());
            rotaEncontrada.setLocalDestino(rotaCreateDTO.getLocalDestino());

            ColaboradorEntity colaboradorEncontrado = colaboradorService.buscarPorId(
                    rotaEncontrada.getColaborador().getIdUsuario());
            colaboradorEncontrado.getRotas().add(rotaEncontrada);

            rotaRepository.save(rotaEncontrada);

            RotaDTO rotaDTO = objectMapper.convertValue(rotaEncontrada, RotaDTO.class);
            rotaDTO.setIdUsuario(colaboradorEncontrado.getIdUsuario());
            return rotaDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletar(Integer idRota) throws RegraDeNegocioException {
        try {
            RotaEntity rotaEncontrada = buscarPorId(idRota);
            rotaEncontrada.setStatus(StatusGeral.INATIVO);
            rotaRepository.save(rotaEncontrada);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }

    public List<RotaDTO> listar() {
        List<RotaDTO> rotasDTO = rotaRepository.findAll()
                .stream()
                .map(rota -> {
                    RotaDTO rotaDTO = objectMapper.convertValue(rota, RotaDTO.class);
                    rotaDTO.setIdUsuario(rota.getColaborador().getIdUsuario());
                    return rotaDTO;
                })
                .toList();

        return rotasDTO;
    }

    public RotaDTO listarPorId(Integer idRota) throws RegraDeNegocioException {
        try {
            RotaEntity rotaRecuperado = buscarPorId(idRota);

            RotaDTO rotaDTO = objectMapper.convertValue(rotaRecuperado, RotaDTO.class);
            rotaDTO.setIdUsuario(rotaRecuperado.getColaborador().getIdUsuario());
            rotaDTO.setIdRota(idRota);
            return rotaDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void cadastrarPosto(Integer idRota, Integer idPosto) throws RegraDeNegocioException {
        try {
            RotaEntity rotaEncontrada = buscarPorId(idRota);
            PostoEntity postoEncontrado = postoService.buscarPorId(idPosto);

            rotaEncontrada.getPostos().add(postoEncontrado);
            postoEncontrado.getRotas().add(rotaEncontrada);

            rotaRepository.save(rotaEncontrada);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cadastro.");
        }
    }

    public RotaComPostosDTO listarPostosCadastrados(Integer idRota) throws RegraDeNegocioException {
        try {
            RotaEntity rotaEncontrada = buscarPorId(idRota);

            RotaComPostosDTO rotaComPostosDTO = objectMapper.convertValue(rotaEncontrada, RotaComPostosDTO.class);
            rotaComPostosDTO.setIdUsuario(rotaEncontrada.getColaborador().getIdUsuario());

            rotaComPostosDTO.setPostos(rotaEncontrada.getPostos()
                    .stream()
                    .map(posto -> { // Converte postos para DTO
                        PostoDTO postoDTO = objectMapper.convertValue(posto, PostoDTO.class);
                        postoDTO.setIdUsuario(posto.getColaborador().getIdUsuario());
                        return postoDTO;
                    })
                    .collect(Collectors.toSet()));

            return rotaComPostosDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public RotaEntity buscarPorId(Integer idRota) throws RegraDeNegocioException {
        return rotaRepository.findById(idRota)
                .orElseThrow(() -> new RegraDeNegocioException("Rota não encontrada"));
    }
}