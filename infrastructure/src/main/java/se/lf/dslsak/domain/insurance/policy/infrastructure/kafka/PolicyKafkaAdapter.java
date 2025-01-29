package se.lf.dslsak.domain.insurance.policy.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.lf.dslsak.domain.insurance.policy.model.Policy;
import se.lf.dslsak.domain.insurance.policy.events.PolicyEvent;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PolicyKafkaAdapter {
    
    public PolicyEvent toDomainEvent(Policy policy) {
        return PolicyEvent.newBuilder()
            .setEventId(UUID.randomUUID().toString())
            .setEventType("POLICY_CREATED")
            .setTimestamp(System.currentTimeMillis())
            .setPolicyId(policy.getPolicyId().toString())
            .setPolicyNumber(policy.getPolicyNumber().toString())
            .setPolicyHolder(policy.getPolicyHolder().toString())
            .setEffectiveDate(policy.getEffectiveDate().toEpochDay())
            .setExpirationDate(policy.getExpirationDate().toEpochDay())
            .setStatus(policy.getStatus().toString())
            .build();
    }
    
    public Policy toDomainModel(PolicyEvent event) {
        return Policy.builder()
            .policyId(event.getPolicyId().toString())
            .policyNumber(event.getPolicyNumber().toString())
            .policyHolder(event.getPolicyHolder().toString())
            .effectiveDate(LocalDate.ofEpochDay(event.getEffectiveDate()))
            .expirationDate(LocalDate.ofEpochDay(event.getExpirationDate()))
            .status(event.getStatus().toString())
            .build();
    }
} 