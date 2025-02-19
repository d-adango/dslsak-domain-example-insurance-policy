package se.lf.dslsak.domain.insurance.policy.ports;

import se.lf.dslsak.domain.insurance.policy.model.Policy;
import java.util.Optional;  

public interface PolicyRepository {
    Optional<Policy> findById(String policyId);
    Policy findByPolicyNumber(String policyNumber);
    Policy save(Policy policy);
    void delete(String id);
} 