package br.com.logisticadbc.service;

import br.com.logisticadbc.entity.mongodb.LogEntity;
import br.com.logisticadbc.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public void save(LogEntity logEntity) {
        var log = new LogEntity();
        BeanUtils.copyProperties(logEntity, log);
        logRepository.save(log);
    }
}
