package se.lf.dslsak.domain.insurance.policy.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.context.annotation.Configuration;
import se.lf.dslsak.platform.core.exception.handler.PlatformExceptionHandler;   

@Configuration
public class CamelConfig extends RouteBuilder {

    private final PlatformExceptionHandler exceptionHandler;

    public CamelConfig(PlatformExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void configure() {
        // Add platform exception handling
        onException(Exception.class)
            .handled(true)
            .process(exceptionHandler);

        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "Insurance Policy API")
            .apiProperty("api.version", "1.0.0");
    }
} 