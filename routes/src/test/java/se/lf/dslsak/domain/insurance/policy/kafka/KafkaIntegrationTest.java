package se.lf.dslsak.domain.insurance.policy.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.lf.dslsak.domain.insurance.policy.PolicyApplication;
import se.lf.dslsak.domain.insurance.policy.config.TestConfig;
import se.lf.dslsak.domain.insurance.policy.config.ExceptionConfig;
import se.lf.dslsak.domain.insurance.policy.config.ObservabilityConfig;
import se.lf.dslsak.domain.insurance.policy.config.KafkaTestConfig;
import se.lf.dslsak.domain.insurance.policy.config.RepositoryTestConfig;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@ContextConfiguration(classes = {
    PolicyApplication.class,
    TestConfig.class,
    ExceptionConfig.class,
    ObservabilityConfig.class,
    KafkaTestConfig.class,
    RepositoryTestConfig.class
})
@EmbeddedKafka(partitions = 1, brokerProperties = { 
    "listeners=PLAINTEXT://localhost:9092", 
    "port=9092" 
})
public class KafkaIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(KafkaIntegrationTest.class);
    private static final String TOPIC = "test-topic";
    private CountDownLatch latch = new CountDownLatch(1);
    private String payload = null;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void testKafkaIntegration() throws Exception {
        String message = "Hello Kafka Test";
        
        log.info("Sending message: {}", message);
        kafkaTemplate.send(TOPIC, message);
        
        boolean messageReceived = latch.await(10, TimeUnit.SECONDS);
        
        assertTrue(messageReceived, "Message was not received");
        assertEquals(message, payload, "Received message doesn't match sent message");
        log.info("Test completed successfully");
    }

    @KafkaListener(topics = TOPIC, groupId = "test-group")
    public void receive(ConsumerRecord<?, ?> consumerRecord, 
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.OFFSET) Long offset) {
        log.info("Received message: {} from topic: {}, partition: {}, offset: {}", 
                consumerRecord.value(), topic, partition, offset);
        payload = consumerRecord.value().toString();
        latch.countDown();
    }
} 