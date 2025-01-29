package se.lf.dslsak.domain.insurance.policy.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HealthCheckRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        rest("/health")
            .get()
                .to("direct:health")
            .get("/ready")
                .to("direct:ready");

        from("direct:health")
            .setBody(constant("OK"));

        from("direct:ready")
            .setBody(constant("READY"));
    }
} 