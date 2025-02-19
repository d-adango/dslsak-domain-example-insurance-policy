package se.lf.dslsak.domain.insurance.policy.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import se.lf.dslsak.domain.insurance.policy.application.PolicyApplicationService; 
import se.lf.dslsak.platform.core.observability.metrics.MetricsService;

@Component
public class PolicyRoutes extends RouteBuilder {

    private final PolicyApplicationService applicationService;
    private final MetricsService metricsService;

    public PolicyRoutes(PolicyApplicationService applicationService, MetricsService metricsService) {
        this.applicationService = applicationService;
        this.metricsService = metricsService;
    }

    @Override
    public void configure() throws Exception {
        // Direct endpoints for testing
        from("direct:get-policy")
            .bean(applicationService, "getPolicy");

        // REST endpoints
        rest("/policies")
            .get("/{policyId}")
                .description("Get policy by ID")
                .outType(Object.class)
                .to("direct:get-policy");

        // Use metrics in routes
        from("direct:createPolicy")
            .process(exchange -> {
                metricsService.incrementCounter("policy.creation.started");
                // ... existing logic ...
                metricsService.incrementCounter("policy.creation.completed");
            });
    }
} 