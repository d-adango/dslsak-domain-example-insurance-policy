package se.lf.dslsak.domain.insurance.policy.application;

import org.springframework.stereotype.Service;
import se.lf.dslsak.domain.insurance.policy.model.Policy;
import se.lf.dslsak.domain.insurance.policy.ports.PolicyRepository;
import se.lf.dslsak.platform.core.exception.factory.ExceptionFactory;
import se.lf.dslsak.platform.core.observability.logging.PlatformLogger;  

import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class PolicyApplicationService {
    private final PolicyRepository policyRepository;
    private final ExceptionFactory exceptionFactory;
    private final PlatformLogger logger;

    public PolicyApplicationService(
            PolicyRepository policyRepository,
            ExceptionFactory exceptionFactory,
            @Qualifier("insuranceLogger") PlatformLogger logger) {
        this.policyRepository = policyRepository;
        this.exceptionFactory = exceptionFactory;
        this.logger = logger;
    }

    public Policy getPolicy(String policyId) {
        return policyRepository.findById(policyId)
            .orElseThrow(() -> exceptionFactory.createNotFoundException("Policy", policyId));
    }
    
    public Policy getPolicyByNumber(String policyNumber) {
        logger.debug("Fetching policy with number: {}", policyNumber);
        Policy policy = policyRepository.findByPolicyNumber(policyNumber);
        if (policy == null) {
            throw exceptionFactory.createNotFoundException("Policy", "number:" + policyNumber);
        }
        return policy;
    }
} 