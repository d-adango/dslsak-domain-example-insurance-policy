package se.lf.dslsak.domain.insurance.policy.streamprocessor;

import org.apache.kafka.streams.StreamsConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ActiveProfiles;
import se.lf.dslsak.domain.insurance.policy.PolicyApplication;
import se.lf.dslsak.domain.insurance.policy.config.*;
import se.lf.dslsak.domain.insurance.policy.model.Policy;    
import se.lf.dslsak.platform.core.stream.processor.StreamProcessor;
import se.lf.dslsak.platform.core.stream.processor.kafka.KafkaStreamProcessor;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {
    PolicyApplication.class,
    TestConfig.class,
    ExceptionConfig.class,
    ObservabilityConfig.class,
    StreamProcessorConfig.class
})
class KafkaIntegrationTest {

    private static final String INPUT_TOPIC = "policy-input";
    private static final String OUTPUT_TOPIC = "policy-output";
    
    @Autowired
    private StreamProcessor<String, Policy> streamProcessor;
    
    @Test
    void testPolicyProcessing() throws Exception {

        // Given
        CountDownLatch latch = new CountDownLatch(1);

        Policy testPolicy = new Policy();
        testPolicy.setPolicy_Nummer("TEST-001");
        
        // When
        streamProcessor.process(INPUT_TOPIC, OUTPUT_TOPIC,
            (key, policy) -> {
                policy.setStatus("PROCESSED");      
                return policy;
            });
        
        streamProcessor.start();
        
        // Then
        boolean completed = latch.await(10, TimeUnit.SECONDS);
        assertTrue(completed, "Processing should complete within timeout");
    }
}