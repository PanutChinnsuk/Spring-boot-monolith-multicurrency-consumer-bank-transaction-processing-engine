package com.project.banksystem.infrastructure.exception.ex;

public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String message) {
        super(message);
    }
}
