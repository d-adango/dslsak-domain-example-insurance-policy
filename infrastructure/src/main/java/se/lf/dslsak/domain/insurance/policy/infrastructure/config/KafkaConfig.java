package se.lf.dslsak.domain.insurance.policy.infrastructure.config;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        
        // Set default values if properties are null
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
            kafkaProperties.getBootstrapServers() != null ? 
            kafkaProperties.getBootstrapServers() : "localhost:9092");
        
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
            kafkaProperties.getProducer().getKeySerializer() != null ? 
            kafkaProperties.getProducer().getKeySerializer() : 
            "org.apache.kafka.common.serialization.StringSerializer");
        
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
            kafkaProperties.getProducer().getValueSerializer() != null ? 
            kafkaProperties.getProducer().getValueSerializer() : 
            KafkaAvroSerializer.class.getName());

        if (kafkaProperties.getSchemaRegistry() != null && 
            kafkaProperties.getSchemaRegistry().getUrl() != null) {
            configProps.put("schema.registry.url", 
                kafkaProperties.getSchemaRegistry().getUrl());
        } else {
            configProps.put("schema.registry.url", "http://localhost:8081");
        }

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
} 