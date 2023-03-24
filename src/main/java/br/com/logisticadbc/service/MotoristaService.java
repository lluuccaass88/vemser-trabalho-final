//package br.com.logisticadbc.service;
//
//import br.com.logisticadbc.dto.in.MotoristaCreateDTO;
//import br.com.logisticadbc.dto.in.MotoristaUpdateDTO;
//import br.com.logisticadbc.dto.out.MotoristaCompletoDTO;
//import br.com.logisticadbc.dto.out.MotoristaDTO;
//import br.com.logisticadbc.dto.out.PageDTO;
//import br.com.logisticadbc.entity.MotoristaEntity;
//import br.com.logisticadbc.entity.enums.StatusGeral;
//import br.com.logisticadbc.entity.enums.StatusMotorista;
//import br.com.logisticadbc.exceptions.RegraDeNegocioException;
//import br.com.logisticadbc.repository.MotoristaRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class MotoristaService {
//
//    private final MotoristaRepository motoristaRepository;
//    private final EmailService emailService;
//    private final ObjectMapper objectMapper;
//
//    public MotoristaDTO criar(MotoristaCreateDTO motoristaCreateDTO) throws RegraDeNegocioException {
//        MotoristaEntity motoristaEntity = objectMapper.convertValue(motoristaCreateDTO, MotoristaEntity.class);
//
//        try {
//            motoristaEntity.setStatus(StatusGeral.ATIVO);
//            motoristaEntity.setStatusMotorista(StatusMotorista.DISPONIVEL);
//
//            motoristaRepository.save(motoristaEntity);
//
//            emailService.enviarEmailBoasVindasMotorista(motoristaEntity);
//
//            return objectMapper.convertValue(motoristaEntity, MotoristaDTO.class);
//
//         } catch (Exception e) {
//            e.printStackTrace();
//            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
//        }
//    }
//    public MotoristaDTO editar(Integer idUsuario, MotoristaUpdateDTO motoristaUpdateDTO) throws RegraDeNegocioException {
//        MotoristaEntity motoristaEntity = buscarPorId(idUsuario);
//
//        try {
//            motoristaEntity.setNome(motoristaUpdateDTO.getNome());
//            motoristaEntity.setSenha(motoristaUpdateDTO.getSenha());
//
//            motoristaRepository.save(motoristaEntity);
//
//            return objectMapper.convertValue(motoristaEntity, MotoristaDTO.class);
//
//        } catch (Exception e) {
//            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
//
//        }
//    }
//
//    public void deletar(Integer idUsuario) throws RegraDeNegocioException {
//        MotoristaEntity motoristaEntity = buscarPorId(idUsuario);
//
//        try {
//            motoristaEntity.setStatus(StatusGeral.INATIVO);
//
//            motoristaRepository.save(motoristaEntity);
//
//        } catch (Exception e) {
//            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
//        }
//    }
//
//    public List<MotoristaDTO> listar() {
//        return motoristaRepository
//                .findAll()
//                .stream()
//                .map(motorista -> objectMapper.convertValue(motorista, MotoristaDTO.class)).toList();
//    }
//
//    public List<MotoristaDTO> listarAtivos() {
//        return motoristaRepository
//                .findAll()
//                .stream()
//                .filter(motorista -> motorista.getStatus().equals(StatusGeral.ATIVO))
//                .map(motorista -> objectMapper.convertValue(motorista, MotoristaDTO.class)).toList();
//    }
//
//    public List<MotoristaDTO> listarInativos() {
//        return motoristaRepository
//                .findAll()
//                .stream()
//                .filter(motorista -> motorista.getStatus().equals(StatusGeral.INATIVO))
//                .map(motorista -> objectMapper.convertValue(motorista, MotoristaDTO.class)).toList();
//    }
//
//    public MotoristaDTO listarPorId(Integer idMotorista) throws RegraDeNegocioException {
//        MotoristaEntity motoristaRecuperado = buscarPorId(idMotorista);
//
//        try {
//            MotoristaDTO motoristaDTO = objectMapper.convertValue(motoristaRecuperado, MotoristaDTO.class);
//            motoristaDTO.setIdUsuario(idMotorista);
//            return motoristaDTO;
//
//        } catch (Exception e) {
//            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
//        }
//    }
//
    public PageDTO<MotoristaCompletoDTO> gerarRelatorioMotoristasInformacoesCompletas(Integer pagina, Integer tamanho){

        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<MotoristaCompletoDTO> paginacaoMotorista = motoristaRepository.relatorio(solicitacaoPagina);

        List<MotoristaCompletoDTO> motoristaDTOList = paginacaoMotorista
                .getContent()
                .stream()
                .map(colaborador -> objectMapper.convertValue(colaborador, MotoristaCompletoDTO.class))
                .toList();

        return new PageDTO<>(
                paginacaoMotorista.getTotalElements(),
                paginacaoMotorista.getTotalPages(),
                pagina,
                tamanho,
                motoristaDTOList
        );
    }
//
//    public PageDTO<MotoristaDTO> listarMotoristaDisponivelEAtivoOrdenadoPorNomeAsc(Integer pagina, Integer tamanho) {
//        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);
//
//        Page<MotoristaEntity> paginacaoMotorista = motoristaRepository
//                .findByStatusMotoristaEqualsAndStatusEqualsOrderByNomeAsc(
//                        solicitacaoPagina, StatusMotorista.DISPONIVEL, StatusGeral.ATIVO);
//
//        List<MotoristaDTO> motoristaDTOList = paginacaoMotorista
//                .getContent()
//                .stream()
//                .map(motorista -> objectMapper.convertValue(motorista, MotoristaDTO.class))
//                .toList();
//
//        return new PageDTO<>(
//                paginacaoMotorista.getTotalElements(),
//                paginacaoMotorista.getTotalPages(),
//                pagina,
//                tamanho,
//                motoristaDTOList
//        );
//    }
//
//    public MotoristaEntity buscarPorId(Integer id) throws RegraDeNegocioException {
//        return motoristaRepository.findById(id)
//                .orElseThrow(() -> new RegraDeNegocioException("Motorista não encontrado"));
//    }
//
//    public void mudarStatus(MotoristaEntity motorista, StatusMotorista status) {
//        motorista.setStatusMotorista(status);
//        motoristaRepository.save(motorista);
//    }
//}