package se.lf.dslsak.domain.insurance.policy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.lf.dslsak.platform.core.exception.factory.ExceptionFactory;

@Configuration
public class ExceptionConfig {
    
    @Bean
    public ExceptionFactory exceptionFactory() {
        return new ExceptionFactory();
    }
} 