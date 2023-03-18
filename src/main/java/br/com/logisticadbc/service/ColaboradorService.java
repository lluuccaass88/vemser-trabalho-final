
package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.in.ColaboradorCreateDTO;

import br.com.logisticadbc.dto.out.ColaboradorCompletoDTO;
import br.com.logisticadbc.dto.out.ColaboradorDTO;
import br.com.logisticadbc.dto.in.ColaboradorUpdateDTO;
import br.com.logisticadbc.dto.out.MotoristaDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.entity.ColaboradorEntity;
import br.com.logisticadbc.entity.MotoristaEntity;
import br.com.logisticadbc.entity.enums.StatusGeral;
import br.com.logisticadbc.entity.enums.StatusMotorista;
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

    //private final CaminhaoService caminhaoService;

    public List<ColaboradorDTO> listar() {
        return colaboradorRepository.findAll()
                .stream()
                .map(colaborador -> objectMapper.convertValue(colaborador, ColaboradorDTO.class))
                .toList();
    }

    public Page<ColaboradorCompletoDTO> gerarRelatorioColaboradoresInformacoesCompletas(Integer pagina, Integer tamanho){
//
//        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);
//
//        Page<ColaboradorCompletoDTO> paginacaoColaborador = colaboradorRepository.relatorio(solicitacaoPagina);
//
//        List<ColaboradorCompletoDTO> colaboradorDTOList = paginacaoColaborador
//
//
//
////                paginacaoColaborador
////                .getContent()
////                .stream()
////                .map(colaborador -> objectMapper.convertValue(colaborador, ColaboradorCompletoDTO.class))
////                .toList();
//
//        return new PageDTO<>(
//                paginacaoColaborador.getTotalElements(),
//                paginacaoColaborador.getTotalPages(),
//                pagina,
//                tamanho,
//                colaboradorDTOList


//        );














        return null; //colaboradorRepository.relatorio();
    }

    // TODO - fazer senha nao retornar no dto

    public ColaboradorDTO criar(ColaboradorCreateDTO colaboradorCreateDTO) throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEntity = objectMapper.convertValue(colaboradorCreateDTO, ColaboradorEntity.class);

            colaboradorEntity.setStatus(StatusGeral.ATIVO);

            colaboradorRepository.save(colaboradorEntity);

           // emailService.enviarEmailBoasVindasColabotador(colaboradorEntity);

            return objectMapper.convertValue(colaboradorEntity, ColaboradorDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }

    public ColaboradorDTO editar(Integer idUsuario, ColaboradorUpdateDTO colaboradorUpdateDTO)
            throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorEncontrado = buscarPorId(idUsuario);

            if (colaboradorEncontrado.getStatus().equals(StatusGeral.INATIVO)) {
                throw new RegraDeNegocioException("Usuário inativo!");
            }

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
        try {
            ColaboradorEntity colaboradorEncontrado = buscarPorId(idUsuario);
            colaboradorEncontrado.setStatus(StatusGeral.INATIVO);

            colaboradorRepository.save(colaboradorEncontrado);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a exclusão.");
        }
    }

    public ColaboradorDTO listarPorId(Integer idColaborador) throws RegraDeNegocioException {
        try {
            ColaboradorEntity colaboradorRecuperado = buscarPorId(idColaborador);

            ColaboradorDTO colaboradorDTO = objectMapper.convertValue(colaboradorRecuperado, ColaboradorDTO.class);
            colaboradorDTO.setIdUsuario(idColaborador);
            return colaboradorDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public ColaboradorEntity buscarPorId(Integer idUsuario) throws RegraDeNegocioException{
        return colaboradorRepository.findById(idUsuario)
                .orElseThrow(() -> new RegraDeNegocioException("Colaborador não encontrado"));
    }
}