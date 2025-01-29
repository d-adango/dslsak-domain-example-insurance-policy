package se.lf.dslsak.domain.insurance.policy.routes;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import se.lf.dslsak.domain.insurance.policy.PolicyApplication;
import se.lf.dslsak.domain.insurance.policy.model.Policy;
import se.lf.dslsak.domain.insurance.policy.ports.PolicyRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@CamelSpringBootTest
@SpringBootTest
@ContextConfiguration(classes = {PolicyApplication.class, PolicyRoutesTest.TestConfig.class})
class PolicyRoutesTest {
    private static final Logger log = LoggerFactory.getLogger(PolicyRoutesTest.class);

    @Configuration
    @ComponentScan(basePackages = {
        "se.lf.dslsak.domain.insurance.policy",
        "se.lf.dslsak.domain.insurance.policy.routes"
    })
    static class TestConfig {
        @Bean
        @Primary
        public PolicyRepository testRepository() {
            log.debug("Creating test repository bean");
            return new TestPolicyRepository();
        }
    }

    static class TestPolicyRepository implements PolicyRepository {
        private static final Logger log = LoggerFactory.getLogger(TestPolicyRepository.class);
        private final Policy testPolicy = createTestPolicy("test-id");

        @Override
        public Optional<Policy> findById(String id) {
            log.debug("Finding policy by ID: {}", id);
            return Optional.of(testPolicy);
        }

        @Override
        public Policy save(Policy policy) {
            log.debug("Saving policy: {}", policy);
            return policy;
        }

        @Override
        public Policy findByPolicyNumber(String policyNumber) {
            log.debug("Finding policy by number: {}", policyNumber);
            return testPolicy;
        }

        @Override
        public void delete(String id) {
            log.debug("Deleting policy with ID: {}", id);
        }
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Test
    void shouldGetPolicyById() {
        log.debug("Starting test: shouldGetPolicyById");
        
        // When
        log.debug("Sending request to direct:get-policy");
        Policy result = producerTemplate.requestBody("direct:get-policy", "test-id", Policy.class);
        
        // Then
        log.debug("Received result: {}", result);
        assertNotNull(result, "Policy should not be null");
        assertEquals("test-id", result.getPolicyId(), "Policy ID should match");
        assertEquals("POL-001", result.getPolicyNumber(), "Policy number should match");
        assertEquals("John Doe", result.getPolicyHolder(), "Policy holder should match");
        assertEquals("ACTIVE", result.getStatus(), "Status should match");
        
        log.debug("Test completed successfully");
    }

    private static Policy createTestPolicy(String policyId) {
        return Policy.builder()
            .policyId(policyId)
            .policyNumber("POL-001")
            .policyHolder("John Doe")
            .status("ACTIVE")
            .effectiveDate(LocalDate.now())
            .expirationDate(LocalDate.now().plusYears(1))
            .build();
    }
} 