package se.lf.dslsak.domain.insurance.policy.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Policy {
    String policyId;
    String policyNumber;
    String policyHolder;
    LocalDate effectiveDate;
    LocalDate expirationDate;
    String status;
} 


