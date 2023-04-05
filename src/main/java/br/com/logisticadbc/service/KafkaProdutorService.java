package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.out.PossiveisClientesDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProdutorService {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value(value = "${kafka.topic}")
    private String topic;

    public void enviarEmailPossiveisClientes(String email, String nome) throws JsonProcessingException {
        Integer particao = 0;

        PossiveisClientesDTO possiveisClientesDTO = new PossiveisClientesDTO(email, nome);

        String mensagem = objectMapper.writeValueAsString(possiveisClientesDTO);

        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(mensagem)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString());

        stringMessageBuilder.setHeader(KafkaHeaders.PARTITION_ID, particao); //Partição

        Message<String> message = stringMessageBuilder.build();

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult result) {
                log.info(" Log enviado para o kafka com o texto: {} ", mensagem);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(" Erro ao publicar duvida no kafka com a mensagem: {}", mensagem, ex);
            }
        });
    }

}