package se.lf.dslsak.domain.insurance.policy.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import se.lf.dslsak.domain.insurance.policy.application.PolicyApplicationService;

@Component
public class PolicyRoutes extends RouteBuilder {

    private final PolicyApplicationService applicationService;

    public PolicyRoutes(PolicyApplicationService applicationService) {
        this.applicationService = applicationService;
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
    }
} 