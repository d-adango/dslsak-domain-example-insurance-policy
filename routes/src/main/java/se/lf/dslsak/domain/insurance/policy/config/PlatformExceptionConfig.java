package se.lf.dslsak.domain.insurance.policy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import se.lf.dslsak.platform.core.exception.handler.PlatformExceptionHandler;

@Configuration
@Import(PlatformExceptionHandler.class)
public class PlatformExceptionConfig {
} 