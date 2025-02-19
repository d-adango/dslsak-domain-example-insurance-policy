package se.lf.dslsak.domain.insurance.policy.processors.inbound;    

import org.springframework.stereotype.Component;
import se.lf.dslsak.domain.insurance.policy.model.Policy;
import se.lf.dslsak.platform.core.stream.processor.StreamProcessor; 

@Component
public class PolicyInboundProcessor {
    private final StreamProcessor<String, Policy> streamProcessor;

    public PolicyInboundProcessor(StreamProcessor<String, Policy> streamProcessor) {
        this.streamProcessor = streamProcessor;
    }

    public void processInbound() {
        streamProcessor.process(
            "external-policy-topic",     
            "internal-policy-topic",     
            this::transformToInternalPolicy
        );
    }

    private Policy transformToInternalPolicy(String key, Policy externalPolicy) {
        // Domain transformation logic here
        return Policy.builder()
            .policyNumber(externalPolicy.getPolicyNumber())
            .status("TRANSFORMED")
            // Add other domain-specific transformations
            .build();
    }
} 