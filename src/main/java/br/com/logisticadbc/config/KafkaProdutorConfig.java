package br.com.logisticadbc.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

public class KafkaProdutorConfig {

    @Value(value = "${kafka.bootstrap-servers}")
    private String bootstrapAddress; // moped.srvs.cloudkafka.com:9094

    @Value(value = "${kafka.properties.sasl.mechanism}")
    private String scram;

    @Value(value = "${kafka.properties.sasl.jaas.config}")
    private String jaasConfig;

    @Value(value = "${kafka.properties.security.protocol}")
    private String sasl;

    @Value(value = "${kafka.properties.enable.idempotence}")
    private String idempotence;

    @Bean
    public KafkaTemplate<String, String> configKafkaTemplate() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress); // servidor
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // chave
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // valor
        configProps.put("sasl.mechanism", scram);
        configProps.put("sasl.jaas.config", jaasConfig);
        configProps.put("security.protocol", sasl);
        configProps.put("enable.idempotence", idempotence);
        DefaultKafkaProducerFactory<String, String> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(configProps);
        return new KafkaTemplate<>(kafkaProducerFactory);
    }
}