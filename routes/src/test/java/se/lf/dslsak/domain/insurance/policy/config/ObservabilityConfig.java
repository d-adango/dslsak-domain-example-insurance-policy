package se.lf.dslsak.domain.insurance.policy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.lf.dslsak.platform.core.observability.logging.PlatformLogger;
import se.lf.dslsak.platform.core.observability.metrics.MetricsService;

import java.util.Map;

@Configuration
public class ObservabilityConfig {
    
    @Bean("insuranceLogger")
    public PlatformLogger insuranceLogger() {
        return new PlatformLogger();
    }

    @Bean
    public MetricsService metricsService() {
        return new MetricsService() {
            @Override
            public void incrementCounter(String name) {
                // No-op implementation
            }

            @Override
            public void recordTimer(String name, Runnable task) {
                // No-op implementation
            }

            @Override
            public void recordMetric(String name, double value) {
                // No-op implementation
            }   

            @Override
            public void recordTimer(String name, Map<String, String> tags, Runnable task) {
                // No-op implementation
            }

            @Override
            public void incrementCounter(String name, Map<String, String> tags) {
                // No-op implementation
            }

            @Override
            public void recordMetric(String name, double value, Map<String, String> tags) {
                // No-op implementation
            }


        };
    }
} 