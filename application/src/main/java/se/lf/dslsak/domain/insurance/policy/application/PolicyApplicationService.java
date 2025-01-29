package se.lf.dslsak.domain.insurance.policy.application;

import org.springframework.stereotype.Service;
import se.lf.dslsak.domain.insurance.policy.model.Policy;
import se.lf.dslsak.domain.insurance.policy.ports.PolicyRepository;

@Service
public class PolicyApplicationService {
    private final PolicyRepository policyRepository;

    public PolicyApplicationService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public Policy getPolicy(String policyId) {
        return policyRepository.findById(policyId)
            .orElseThrow(() -> new RuntimeException("Policy not found: " + policyId));
    }
    
    public Policy getPolicyByNumber(String policyNumber) {
        Policy policy = policyRepository.findByPolicyNumber(policyNumber);
        if (policy == null) {
            throw new RuntimeException("Policy not found with number: " + policyNumber);
        }
        return policy;
    }
} 