package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.MotoristaCreateDTO;
import br.com.logisticadbc.dto.out.ColaboradorDTO;
import br.com.logisticadbc.dto.out.MotoristaDTO;
import br.com.logisticadbc.dto.in.MotoristaUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.MotoristaEntity;
import br.com.logisticadbc.entity.enums.StatusMotorista;
import br.com.logisticadbc.entity.enums.StatusUsuario;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.MotoristaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;


    // TODO - MODIFICAR A SENHA PARA NAO RETORNAR NO DTO

    public MotoristaDTO criar(MotoristaCreateDTO motoristaCreateDTO) throws RegraDeNegocioException {
        try {
            MotoristaEntity motoristaEntity = objectMapper.convertValue(motoristaCreateDTO, MotoristaEntity.class);

            motoristaEntity.setStatusUsuario(StatusUsuario.ATIVO);
            motoristaEntity.setStatusMotorista(StatusMotorista.DISPONIVEL);

            motoristaRepository.save(motoristaEntity);
            log.info("MotoristaEntity: {}", motoristaEntity);

            emailService.enviarEmailBoansVindasMotorista(motoristaEntity);

            return objectMapper.convertValue(motoristaEntity, MotoristaDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }
    public MotoristaDTO editar(Integer idUsuario, MotoristaUpdateDTO motoristaUpdateDTO) throws RegraDeNegocioException {
        try {
            MotoristaEntity motoristaEntity = buscarPorId(idUsuario);

            motoristaEntity.setNome(motoristaUpdateDTO.getNome());
            motoristaEntity.setSenha(motoristaUpdateDTO.getSenha());

            motoristaRepository.save(motoristaEntity);

            return objectMapper.convertValue(motoristaEntity, MotoristaDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }
    public void deletar(Integer id) throws RegraDeNegocioException {
        try {
            MotoristaEntity motoristaEntity = buscarPorId(id);
            motoristaEntity.setStatusUsuario(StatusUsuario.INATIVO);

            motoristaRepository.save(motoristaEntity);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }

    public List<MotoristaDTO> listar() {
        return motoristaRepository
                .findAll()
                .stream()
                .map(motorista -> objectMapper.convertValue(motorista, MotoristaDTO.class)).toList();
    }

    public MotoristaDTO listarPorId(Integer idMotorista) throws RegraDeNegocioException {
        MotoristaEntity motoristaRecuperado = buscarPorId(idMotorista);
        MotoristaDTO motoristaDTO = objectMapper.convertValue(motoristaRecuperado, MotoristaDTO.class);
        motoristaDTO.setIdUsuario(idMotorista);
        return motoristaDTO;
    }

    public MotoristaEntity buscarPorId(Integer id) throws RegraDeNegocioException {
        return motoristaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Motorista não encontrado"));
    }

    public void mudarStatus(MotoristaEntity motorista, StatusMotorista status) {
        motorista.setStatusMotorista(status);
        motoristaRepository.save(motorista);
    }

    public PageDTO<MotoristaDTO> listarMotoristaDisponivelEAtivoOrdenadoPorNomeAsc(Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<MotoristaEntity> paginacaoMotorista = motoristaRepository.findByStatusMotoristaEqualsAndStatusUsuarioEqualsOrderByNomeAsc(
                solicitacaoPagina, StatusMotorista.DISPONIVEL, StatusUsuario.ATIVO);

        List<MotoristaDTO> motoristaDTOList = paginacaoMotorista
                .getContent()
                .stream()
                .map(motorista -> objectMapper.convertValue(motorista, MotoristaDTO.class))
                .toList();

        return new PageDTO<>(
                paginacaoMotorista.getTotalElements(),
                paginacaoMotorista.getTotalPages(),
                pagina,
                tamanho,
                motoristaDTOList
        );
    }
}