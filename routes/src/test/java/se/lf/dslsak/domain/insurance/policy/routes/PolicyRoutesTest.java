package se.lf.dslsak.domain.insurance.policy.routes;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import se.lf.dslsak.domain.insurance.policy.PolicyApplication;
import se.lf.dslsak.domain.insurance.policy.model.Policy; 
import se.lf.dslsak.domain.insurance.policy.ports.PolicyRepository;
import se.lf.dslsak.platform.core.observability.metrics.MetricsService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@CamelSpringBootTest
@ContextConfiguration(classes = {PolicyApplication.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PolicyRoutesTest {
    private static final Logger log = LoggerFactory.getLogger(PolicyRoutesTest.class);

    @MockBean
    private MetricsService metricsService;

    @MockBean
    private PolicyRepository testRepository;

    @javax.annotation.Resource
    private ProducerTemplate producerTemplate;

    @Test
    void shouldGetPolicyById() {
        log.debug("Starting test: shouldGetPolicyById");
        
        // Given
        Policy testPolicy = createTestPolicy("test-id");
        when(testRepository.findById("test-id")).thenReturn(Optional.of(testPolicy));
        
        // When
        log.debug("Sending request to direct:get-policy");
        Policy result = producerTemplate.requestBody("direct:get-policy", "test-id", Policy.class);
        
        // Then
        log.debug("Received result: {}", result);
        assertNotNull(result, "Policy should not be null");
        assertEquals("test-id", result.getPolicyId(), "Policy ID should match");
        assertEquals("POL-001", result.getPolicy_Nummer(), "Policy number should match");
        assertEquals("John Doe", result.getPolicy_Innehavare(), "Policy holder should match");
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