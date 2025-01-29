package se.lf.dslsak.domain.insurance.policy.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig extends RouteBuilder {
    @Override
    public void configure() {
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "Insurance Policy API")
            .apiProperty("api.version", "1.0.0");
    }
} 