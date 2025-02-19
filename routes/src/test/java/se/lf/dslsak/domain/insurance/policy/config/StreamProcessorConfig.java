package se.lf.dslsak.domain.insurance.policy.config;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.lf.dslsak.platform.core.stream.processor.StreamProcessor;     
import se.lf.dslsak.platform.core.stream.processor.kafka.KafkaStreamProcessor;
import se.lf.dslsak.domain.insurance.policy.model.Policy;
 
import java.util.Properties;

@Configuration
public class StreamProcessorConfig {

    @Bean
    public StreamProcessor<String, Policy> streamProcessor() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "policy-processor-test");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        
        return new KafkaStreamProcessor<>(config);
    }
} 