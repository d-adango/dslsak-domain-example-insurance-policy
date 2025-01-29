package se.lf.dslsak.domain.insurance.policy.model;

import lombok.Builder;
import lombok.Value;
import java.time.LocalDate;

@Value
@Builder
public class Policy {
    String policyId;
    String policyNumber;
    String policyHolder;
    LocalDate effectiveDate;
    LocalDate expirationDate;
    String status;
} 


