package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.out.LogDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.entity.UsuarioEntity;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.entity.mongodb.LogEntity;
import br.com.logisticadbc.exceptions.RegraDeNegocioException;
import br.com.logisticadbc.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;


    // TODO - METODO CRIADO PORÉM NAO SEI SE SERÁ UTILIZADO
    public PageDTO<LogDTO> listAllLogs(Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);

        Page<LogEntity> paginacaoLog = logRepository.findAll(solicitacaoPagina);

        List<LogDTO> logDTOList = paginacaoLog
                .getContent()
                .stream()
                .map(log -> objectMapper.convertValue(log, LogDTO.class))
                .toList();

        return new PageDTO<>(
                paginacaoLog.getTotalElements(),
                paginacaoLog.getTotalPages(),
                pagina,
                tamanho,
                logDTOList
        );

    }

    public void gerarLog(UsuarioEntity usuario, String descricao, TipoOperacao tipoOperacao) throws RegraDeNegocioException {
        Integer idUsuario = usuario.getIdUsuario();
        UsuarioEntity usuarioEntity = usuarioService.buscarPorId(idUsuario);

        LogEntity log = new LogEntity();
        log.setLoginOperador(usuarioEntity.getLogin());
        log.setDescricao(descricao);
        log.setTipoOperacao(tipoOperacao);

        try {
            logRepository.save(log);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação do log.");
        }
    }
}