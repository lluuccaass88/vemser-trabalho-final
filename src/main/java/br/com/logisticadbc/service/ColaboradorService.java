
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.ColaboradorCreateDTO;
import br.com.logisticadbc.dto.in.ColaboradorUpdateDTO;
import br.com.logisticadbc.dto.out.ColaboradorCompletoDTO;
import br.com.logisticadbc.dto.out.ColaboradorDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.ColaboradorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public ColaboradorDTO criar(ColaboradorCreateDTO colaboradorCreateDTO) throws RegraDeNegocioException {
        ColaboradorEntity colaboradorEntity = objectMapper.convertValue(colaboradorCreateDTO, ColaboradorEntity.class);

        try {
            colaboradorEntity.setStatus(StatusGeral.ATIVO);

            colaboradorRepository.save(colaboradorEntity);

//            emailService.enviarEmailBoasVindasColabotador(colaboradorEntity);

            return objectMapper.convertValue(colaboradorEntity, ColaboradorDTO.class);

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public ColaboradorDTO editar(Integer idUsuario, ColaboradorUpdateDTO colaboradorUpdateDTO)
            throws RegraDeNegocioException {
        ColaboradorEntity colaboradorEncontrado = buscarPorId(idUsuario);

        try {
            colaboradorEncontrado.setNome(colaboradorUpdateDTO.getNome());
            colaboradorEncontrado.setSenha(colaboradorUpdateDTO.getSenha());

            colaboradorRepository.save(colaboradorEncontrado);

            return objectMapper.convertValue(colaboradorEncontrado, ColaboradorDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição.");
        }
    }

    public void deletar(Integer idUsuario) throws RegraDeNegocioException {
        ColaboradorEntity colaboradorEncontrado = buscarPorId(idUsuario);

        try {
            colaboradorEncontrado.setStatus(StatusGeral.INATIVO);

            colaboradorRepository.save(colaboradorEncontrado);

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }

    public List<ColaboradorDTO> listar() {
        return colaboradorRepository.findAll()
                .stream()
                .map(colaborador -> objectMapper.convertValue(colaborador, ColaboradorDTO.class))
                .toList();
    }

    public List<ColaboradorDTO> listarAtivos() {
        return colaboradorRepository.findAll()
                .stream()
                .filter(colaborador -> colaborador.getStatus().equals(StatusGeral.ATIVO))
                .map(colaborador -> objectMapper.convertValue(colaborador, ColaboradorDTO.class))
                .toList();
    }

    public List<ColaboradorDTO> listarInativos() {
        return colaboradorRepository.findAll()
                .stream()
                .filter(colaborador -> colaborador.getStatus().equals(StatusGeral.INATIVO))
                .map(colaborador -> objectMapper.convertValue(colaborador, ColaboradorDTO.class))
                .toList();
    }

    public ColaboradorDTO listarPorId(Integer idColaborador) throws RegraDeNegocioException {
        ColaboradorEntity colaboradorRecuperado = buscarPorId(idColaborador);

        try {
            ColaboradorDTO colaboradorDTO = objectMapper.convertValue(colaboradorRecuperado, ColaboradorDTO.class);
            colaboradorDTO.setIdUsuario(idColaborador);
            return colaboradorDTO;

        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public PageDTO<ColaboradorCompletoDTO> gerarRelatorioColaboradoresInformacoesCompletas(Integer pagina, Integer tamanho){

        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<ColaboradorCompletoDTO> paginacaoColaborador = colaboradorRepository.relatorio(solicitacaoPagina);

        List<ColaboradorCompletoDTO> colaboradorDTOList = paginacaoColaborador
                .getContent()
                .stream()
                .map(colaborador -> objectMapper.convertValue(colaborador, ColaboradorCompletoDTO.class))
                .toList();

        return new PageDTO<>(
                paginacaoColaborador.getTotalElements(),
                paginacaoColaborador.getTotalPages(),
                pagina,
                tamanho,
                colaboradorDTOList
        );
    }

    public ColaboradorEntity buscarPorId(Integer idUsuario) throws RegraDeNegocioException{
        return colaboradorRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Colaborador não encontrado"));
    }
}