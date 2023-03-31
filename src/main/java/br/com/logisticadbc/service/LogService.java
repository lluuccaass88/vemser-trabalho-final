package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.out.LogDTO;
import br.com.logisticadbc.entity.mongodb.LogEntity;
import br.com.logisticadbc.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    public void save(LogDTO logDTO) {
        var log = new LogEntity();
        BeanUtils.copyProperties(logDTO, log);
        logRepository.save(log);
    }

    // TODO - METODO CRIADO PORÉM NAO SEI SE SERÁ UTILIZADO
    public List<LogDTO> listAllLogs() {
        return logRepository
                .findAll()
                .stream()
                .map(log -> objectMapper.convertValue(log, LogDTO.class))
                .collect(Collectors.toList());
    }
}