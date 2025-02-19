package se.lf.dslsak.domain.insurance.policy.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.lf.dslsak.domain.insurance.policy.inbound.provider.systema.SystemAPolicy;
import se.lf.dslsak.domain.insurance.policy.model.Policy;

@Mapper(componentModel = "spring")
public interface SystemAPolicyMapper {
    
    @Mapping(target = "policyNumber", source = "policy_Nummer")
    @Mapping(target = "policyHolder", source = "policy_Innehavare")
    Policy toDomain(SystemAPolicy systemAPolicy);

} 