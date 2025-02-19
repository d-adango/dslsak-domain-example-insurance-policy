package se.lf.dslsak.domain.insurance.policy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.lf.dslsak.domain.insurance.policy.ports.PolicyRepository;
import se.lf.dslsak.domain.insurance.policy.model.Policy;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@Configuration
public class RepositoryTestConfig {
    
    @Bean
    public PolicyRepository policyRepository() {
        return new PolicyRepository() {
            private final Map<String, Policy> policies = new HashMap<>();
            
            @Override   
            public Policy findByPolicyNumber(String policyNumber) {
                return policies.get(policyNumber);
            }

            @Override
            public Optional<Policy> findById(String id) {
                return Optional.ofNullable(policies.get(id));
            }

            @Override
            public void delete(String policyNumber) {
                policies.remove(policyNumber);
            }

            @Override
            public Policy save(Policy policy) {
                policies.put(policy.getPolicy_Nummer(), policy);
                return policy;
            }
        };
    }
} 