package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.out.LogDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.entity.mongodb.LogEntity;
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

    public void gerarLog(String loginOperador, String descricao, TipoOperacao tipoOperacao) {
        LogEntity log = new LogEntity();

        log.setLoginOperador(loginOperador);
        log.setDescricao(descricao);
        log.setTipoOperacao(tipoOperacao);

        logRepository.save(log);
    }
}