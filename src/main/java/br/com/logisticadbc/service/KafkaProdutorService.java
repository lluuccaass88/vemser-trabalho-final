package br.com.logisticadbc.service;

import br.com.logisticadbc.dto.kafka.*;
import br.com.logisticadbc.dto.out.LogDTO;
import br.com.logisticadbc.dto.out.LogPorDiaDTO;
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

import java.util.List;
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
                log.info("Produzido com sucesso | enviarEmailPossiveisClientes | {} ", email);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Erro ao produzir | enviarEmailPossiveisClientes | {}", email, ex);
            }
        });
    }

    public void enviarEmailBoasVindas(String email, String nome, String cargo, String login) throws JsonProcessingException {
        Integer particao = 1;

        UsuarioBoasVindasDTO usuarioBoasVindasDTO = new UsuarioBoasVindasDTO(email, nome, cargo, login);

        String mensagem = objectMapper.writeValueAsString(usuarioBoasVindasDTO);

        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(mensagem)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString());

        stringMessageBuilder.setHeader(KafkaHeaders.PARTITION_ID, particao); //Partição

        Message<String> message = stringMessageBuilder.build();

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult result) {
                log.info("Produzido com sucesso | enviarEmailBoasVindas | {} ", email);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Erro ao produzir | enviarEmailBoasVindas | {}", email, ex);
            }
        });
    }

    public void enviarEmailRecuperarSenha(String email, String nome, String senha) throws JsonProcessingException {
        Integer particao = 2;

        UsuarioRecuperaSenhaDTO usuarioRecuperaSenhaDTO = new UsuarioRecuperaSenhaDTO(email, nome, senha);

        String mensagem = objectMapper.writeValueAsString(usuarioRecuperaSenhaDTO);

        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(mensagem)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString());

        stringMessageBuilder.setHeader(KafkaHeaders.PARTITION_ID, particao); //Partição

        Message<String> message = stringMessageBuilder.build();

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult result) {
                log.info("Produzido com sucesso | enviarEmailRecuperarSenha | {} ", email);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Erro ao produzir | enviarEmailRecuperarSenha | {}", email, ex);
            }
        });
    }

    public void enviarEmailViagem(String email, String nome,
                                  String partidaRota, String destinoRota,
                                  String inicioViagem, String fimViagem) throws JsonProcessingException {
        Integer particao = 3;

        ViagemCriadaDTO viagemCriadaDTO = new ViagemCriadaDTO(
                email, nome, partidaRota, destinoRota, inicioViagem, fimViagem);

        String mensagem = objectMapper.writeValueAsString(viagemCriadaDTO);

        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(mensagem)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString());

        stringMessageBuilder.setHeader(KafkaHeaders.PARTITION_ID, particao); //Partição

        Message<String> message = stringMessageBuilder.build();

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult result) {
                log.info("Produzido com sucesso | enviarEmailViagem | {} ", email);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Erro ao produzir | enviarEmailViagem | {}", email, ex);
            }
        });
    }

    public void enviarEmailAdminPossiveisClientes(List<PossiveisClientesDTO> listaPossiveisClientes) throws JsonProcessingException {
        Integer particao = 4;

        ListaPossiveisClientesDTO listaPossiveisClientesDTO = new ListaPossiveisClientesDTO();
        listaPossiveisClientesDTO.setListaPossiveisClientes(listaPossiveisClientes);

        String mensagem = objectMapper.writeValueAsString(listaPossiveisClientesDTO);

        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(mensagem)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString());

        stringMessageBuilder.setHeader(KafkaHeaders.PARTITION_ID, particao); //Partição

        Message<String> message = stringMessageBuilder.build();

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult result) {
                log.info("Produzido com sucesso | enviarEmailAdminPossiveisClientes");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Erro ao produzir | enviarEmailAdminPossiveisClientes ", ex);
            }
        });
    }

    public void enviarLogPorDia(List<LogDTO> listaLog) throws JsonProcessingException {
        Integer particao = 5;

        LogPorDiaDTO logPorDiaDTO = new LogPorDiaDTO();
        logPorDiaDTO.setListDTo(listaLog);

        String mensagem = objectMapper.writeValueAsString(logPorDiaDTO);

        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(mensagem)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString());

        stringMessageBuilder.setHeader(KafkaHeaders.PARTITION_ID, particao); //Partição

        Message<String> message = stringMessageBuilder.build();

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult result) {
                log.info("Produzido com sucesso | enviarLogPorDia");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Erro ao produzir | enviarLogPorDia ", ex);
            }
        });
    }
}