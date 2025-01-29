package se.lf.dslsak.domain.insurance.policy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "se.lf.dslsak.domain.insurance.policy",
    "se.lf.dslsak.domain.insurance.policy.routes",
    "se.lf.dslsak.domain.insurance.policy.application",
    "se.lf.dslsak.domain.insurance.policy.infrastructure"
})
public class PolicyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PolicyApplication.class, args);
    }
} 