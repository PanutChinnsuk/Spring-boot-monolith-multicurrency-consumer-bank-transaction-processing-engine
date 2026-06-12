package com.project.banksystem.infrastructure.exception.ex;

public class MaximumLoginAttemptExceededException extends RuntimeException {
    public MaximumLoginAttemptExceededException(String message) {
        super(message);
    }
}
