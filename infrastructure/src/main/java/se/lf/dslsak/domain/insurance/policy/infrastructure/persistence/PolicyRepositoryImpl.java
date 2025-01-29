package se.lf.dslsak.domain.insurance.policy.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.lf.dslsak.domain.insurance.policy.model.Policy;
import se.lf.dslsak.domain.insurance.policy.ports.PolicyRepository;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class PolicyRepositoryImpl implements PolicyRepository {
    
    private final ConcurrentHashMap<String, Policy> store = new ConcurrentHashMap<>();
    
    @Override
    public Optional<Policy> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
    
    @Override
    public Policy save(Policy policy) {
        store.put(policy.getPolicyId(), policy);
        return policy;
    }
    
    @Override
    public Policy findByPolicyNumber(String policyNumber) {
        return store.values().stream()
            .filter(p -> p.getPolicyNumber().equals(policyNumber))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public void delete(String id) {
        store.remove(id);
    }
} 