package se.lf.dslsak.domain.insurance.policy.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {
    // Exclude specific auto configurations if they cause issues in tests,
    // but do not directly reference Micrometer libraries here.
})
public class TestConfig {
} 