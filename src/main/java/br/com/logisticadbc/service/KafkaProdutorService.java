package br.com.logisticadbc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProdutorService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value(value = "${kafka.topic}")
    private String topic;

    public void sendTo(String mensagem) {
        kafkaTemplate.send(topic, mensagem);
    }

    public void sendTo(String mensagem, String key) {
        kafkaTemplate.send(topic, key, mensagem);
    }

    public void sendTo(String mensagem, String key, int partition) {
        kafkaTemplate.send(topic, partition, key, mensagem);
    }
}