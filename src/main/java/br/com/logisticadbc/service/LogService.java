package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.out.LogDTO;
import br.com.logisticadbc.dto.out.PageDTO;
import br.com.logisticadbc.entity.enums.TipoOperacao;
import br.com.logisticadbc.entity.mongodb.LogEntity;
import br.com.logisticadbc.repository.LogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private final LogRepository logRepository;
    private final KafkaProdutorService kafkaProdutorService;
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

    public List<LogDTO> listAllLogsForDay() {
        String dataAtual = DateTimeFormatter.ofPattern("MM dd yyyy")
                .format(LocalDateTime.now());

        List<LogEntity> logEntityData = logRepository.findByData(dataAtual);

        List<LogDTO> listDTo = logEntityData
                .stream()
                .map(log -> objectMapper.convertValue(log, LogDTO.class))
                .toList();

        return listDTo;
    }


    public void gerarLog(String loginOperador, String descricao, TipoOperacao tipoOperacao) {
        LogEntity log = new LogEntity();

        String dataAtual = DateTimeFormatter.ofPattern("MM dd yyyy")
                .format(LocalDateTime.now());

        log.setLoginOperador(loginOperador);
        log.setDescricao(descricao);
        log.setTipoOperacao(tipoOperacao);

        log.setData(dataAtual);

        logRepository.save(log);
    }

    // executa conta o tempo à partir do íncio da execução do método
    @Scheduled(cron = "*/10 * * * * *")
    public void reportCurrentTime() throws JsonProcessingException {
        List<LogDTO> listaLogs = listAllLogsForDay();

        if (listaLogs.isEmpty()) {
            // não manda email
        } else {
            kafkaProdutorService.enviarLogPorDia(listaLogs);
        }
    }

}