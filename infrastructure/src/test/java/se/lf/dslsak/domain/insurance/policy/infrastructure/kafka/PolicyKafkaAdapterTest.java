package se.lf.dslsak.domain.insurance.policy.infrastructure.kafka;

import org.junit.jupiter.api.Test;
import se.lf.dslsak.domain.insurance.policy.events.PolicyEvent;
import se.lf.dslsak.domain.insurance.policy.model.Policy;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PolicyKafkaAdapterTest {

    private final PolicyKafkaAdapter adapter = new PolicyKafkaAdapter();

    @Test
    void shouldConvertPolicyToDomainEvent() {
        // Given
        String policyId = UUID.randomUUID().toString();
        Policy policy = Policy.builder()
            .policyId(policyId)
            .policyNumber("POL-001")
            .policyHolder("John Doe")
            .status("ACTIVE")
            .effectiveDate(LocalDate.now())
            .expirationDate(LocalDate.now().plusYears(1))
            .build();

        // When
        PolicyEvent event = adapter.toDomainEvent(policy);

        // Then
        assertNotNull(event);
        assertEquals(policyId, event.getPolicyId().toString());
        assertEquals("POL-001", event.getPolicyNumber().toString());
        assertEquals("John Doe", event.getPolicyHolder().toString());
        assertEquals("ACTIVE", event.getStatus().toString());
        assertEquals(policy.getEffectiveDate().toEpochDay(), event.getEffectiveDate());
        assertEquals(policy.getExpirationDate().toEpochDay(), event.getExpirationDate());
    }

    @Test
    void shouldConvertEventToDomainModel() {
        // Given
        LocalDate now = LocalDate.now();
        String policyId = UUID.randomUUID().toString();
        PolicyEvent event = PolicyEvent.newBuilder()
            .setEventId(UUID.randomUUID().toString())
            .setEventType("POLICY_CREATED")
            .setTimestamp(System.currentTimeMillis())
            .setPolicyId(policyId)
            .setPolicyNumber("POL-001")
            .setPolicyHolder("John Doe")
            .setStatus("ACTIVE")
            .setEffectiveDate(now.toEpochDay())
            .setExpirationDate(now.plusYears(1).toEpochDay())
            .build();

        // When
        Policy policy = adapter.toDomainModel(event);

        // Then
        assertNotNull(policy);
        assertEquals(policyId, policy.getPolicyId());
        assertEquals("POL-001", policy.getPolicyNumber());
        assertEquals("John Doe", policy.getPolicyHolder());
        assertEquals("ACTIVE", policy.getStatus());
        assertEquals(now, policy.getEffectiveDate());
        assertEquals(now.plusYears(1), policy.getExpirationDate());
    }
} 