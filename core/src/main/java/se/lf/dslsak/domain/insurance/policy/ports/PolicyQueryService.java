package se.lf.dslsak.domain.insurance.policy.ports;

import se.lf.dslsak.domain.insurance.policy.model.Policy;
import java.util.Optional;
import java.util.List;

public interface PolicyQueryService {
    Optional<Policy> getPolicy(String policyNumber);
    List<Policy> getPoliciesByHolder(String policyHolder);
} 