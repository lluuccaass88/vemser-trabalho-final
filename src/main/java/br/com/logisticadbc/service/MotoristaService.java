package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.MotoristaCreateDTO;
import br.com.logisticadbc.dto.out.ColaboradorCompletoDTO;
import br.com.logisticadbc.dto.out.MotoristaCompletoDTO;
import br.com.logisticadbc.dto.out.MotoristaDTO;
import br.com.logisticadbc.dto.in.MotoristaUpdateDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.entity.MotoristaEntity;
import br.com.logisticadbc.entity.enums.StatusMotorista;
import br.com.logisticadbc.entity.enums.StatusGeral;
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
            motoristaEntity.setStatus(StatusGeral.ATIVO);
            motoristaEntity.setStatusMotorista(StatusMotorista.DISPONIVEL);

            motoristaRepository.save(motoristaEntity);

            emailService.enviarEmailBoasVindasMotorista(motoristaEntity);

            return objectMapper.convertValue(motoristaEntity, MotoristaDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }
    public MotoristaDTO editar(Integer idUsuario, MotoristaUpdateDTO motoristaUpdateDTO) throws RegraDeNegocioException {
        try {
            MotoristaEntity motoristaEntity = buscarPorId(idUsuario);

            if (motoristaEntity.getStatus().equals(StatusGeral.INATIVO)) {
                throw new RegraDeNegocioException("Usuário inativo!");
            }

            motoristaEntity.setNome(motoristaUpdateDTO.getNome());
            motoristaEntity.setSenha(motoristaUpdateDTO.getSenha());

            motoristaRepository.save(motoristaEntity);

            return objectMapper.convertValue(motoristaEntity, MotoristaDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletar(Integer idUsuario) throws RegraDeNegocioException {
        try {
            MotoristaEntity motoristaEntity = buscarPorId(idUsuario);
            motoristaEntity.setStatus(StatusGeral.INATIVO);

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

    public List<MotoristaCompletoDTO> gerarRelatorioMotoristasInformacoesCompletas(){
        return null;//motoristaRepository.relatorio();
    }

    public MotoristaDTO listarPorId(Integer idMotorista) throws RegraDeNegocioException {
        try {
            MotoristaEntity motoristaRecuperado = buscarPorId(idMotorista);

            MotoristaDTO motoristaDTO = objectMapper.convertValue(motoristaRecuperado, MotoristaDTO.class);
            motoristaDTO.setIdUsuario(idMotorista);
            return motoristaDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
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

        Page<MotoristaEntity> paginacaoMotorista = motoristaRepository
                .findByStatusMotoristaEqualsAndStatusEqualsOrderByNomeAsc(
                solicitacaoPagina, StatusMotorista.DISPONIVEL, StatusGeral.ATIVO);

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