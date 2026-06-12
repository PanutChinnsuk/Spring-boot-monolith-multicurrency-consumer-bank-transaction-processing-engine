package com.project.banksystem.infrastructure.exception.ex;

public class InsufficientFundException extends RuntimeException {
    public InsufficientFundException(String message) {
        super(message);
    }
}
