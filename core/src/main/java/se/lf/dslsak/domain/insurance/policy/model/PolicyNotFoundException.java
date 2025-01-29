package se.lf.dslsak.domain.insurance.policy.model;

public class PolicyNotFoundException extends RuntimeException {
    public PolicyNotFoundException(String message) {
        super(message);
    }
} 