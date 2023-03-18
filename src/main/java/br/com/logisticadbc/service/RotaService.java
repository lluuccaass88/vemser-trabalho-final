package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.RotaCreateDTO;
import br.com.logisticadbc.dto.out.RotaDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.RotaEntity;
import br.com.logisticadbc.entity.enums.StatusUsuario;
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

    // TODO fazer metodo de cadastrar posto em rota

    private final RotaRepository rotaRepository;
    private final ObjectMapper objectMapper;
    private final ColaboradorService colaboradorService;

    public RotaDTO criar(Integer idUsuario, RotaCreateDTO rotaCreateDTO) throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEncontrado = colaboradorService.buscarPorId(idUsuario);

            if (colaboradorEncontrado.getStatusUsuario().equals(StatusUsuario.INATIVO)) {
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

    // TODO CRIAR FUNÇÃO PARA SETAR POSTO EM ROTA

    public void deletar(Integer idRota) throws RegraDeNegocioException {
        try {
            RotaEntity rotaEncontrada = buscarPorId(idRota);

            rotaRepository.deleteById(rotaEncontrada.getIdRota());

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

    public RotaEntity buscarPorId(Integer idRota) throws RegraDeNegocioException {
        return rotaRepository.findById(idRota)
                .orElseThrow(() -> new RegraDeNegocioException("Rota não encontrada"));
    }
}