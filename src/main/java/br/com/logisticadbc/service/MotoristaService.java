package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.MotoristaCreateDTO;
import br.com.logisticadbc.dto.MotoristaDTO;
import br.com.logisticadbc.entity.MotoristaEntity;
import br.com.logisticadbc.entity.enums.StatusMotorista;
import br.com.logisticadbc.entity.enums.StatusUsuario;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.MotoristaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final ObjectMapper objectMapper;

    // TODO quando for criar ou alterar o motorista , verificar o status

    // TODO CRUD completo nome metodos -> CRIAR , EDITAR, LISTAR, DELETAR E BUSCARPORID

    public List<MotoristaDTO> listar() {
        return motoristaRepository
                .findAll()
                .stream()
                .map(motorista -> objectMapper.convertValue(motorista, MotoristaDTO.class)).toList();
    }

    // o metodo buscar por id retornando o obj motorista é privado por expor a entidade
    private MotoristaEntity buscarPorId(Integer id) throws RegraDeNegocioException {
        return motoristaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Motorista não encontrado"));
    }

    public MotoristaDTO criar(MotoristaCreateDTO motoristaCreateDTO) throws RegraDeNegocioException {
        // regra de negocio -> ao criar um motorista ele ja esta ativo e disponivel
        try {
            MotoristaEntity motoristaEntity = objectMapper.convertValue(motoristaCreateDTO, MotoristaEntity.class);
            log.info("MotoristaEntity: {}", motoristaEntity);

            motoristaEntity.setStatusUsuario(StatusUsuario.ATIVO);
            motoristaEntity.setStatusMotorista(StatusMotorista.DISPONIVEL);

            motoristaRepository.save(motoristaEntity);

            MotoristaDTO motoristaDTO = objectMapper.convertValue(motoristaEntity, MotoristaDTO.class);

            return motoristaDTO;
        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }
}
