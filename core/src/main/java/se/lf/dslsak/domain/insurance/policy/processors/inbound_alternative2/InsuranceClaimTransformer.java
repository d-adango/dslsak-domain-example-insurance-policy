package se.lf.dslsak.inbound.alternative2.streaming;

import se.lf.dslsak.platform.core.stream.processor.template.ExternalToDomainTemplate;
import se.lf.dslsak.platform.core.model.DomainEventEnvelope;

public class InsuranceClaimTransformer 
    extends ExternalToDomainTemplate<String, ExternalClaim, DomainClaim> {
    
    private static final String SOURCE = "external.claims";
    private static final String VERSION = "1.0.0";
    
    @Override
    protected DomainClaim transformToDomain(ExternalClaim externalClaim) {
        return DomainClaim.builder()
            .claimId(externalClaim.getId())
            .policyNumber(externalClaim.getPolicyId())
            .amount(externalClaim.getAmount())
            .status(mapStatus(externalClaim.getStatus()))
            .build();
    }
    
    @Override
    protected String getSource() {
        return SOURCE;
    }
    
    @Override
    protected String getVersion() {
        return VERSION;
    }
    
    // ... other methods remain the same
} 