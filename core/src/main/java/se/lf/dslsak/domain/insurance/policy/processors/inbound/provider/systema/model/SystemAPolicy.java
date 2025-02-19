package se.lf.dslsak.domain.insurance.policy.inbound.provider.systema;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemAPolicy {
    private String policy_Nummer;
    private String policy_Innehavare;

} 