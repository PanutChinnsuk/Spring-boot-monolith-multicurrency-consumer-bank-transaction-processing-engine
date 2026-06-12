package com.project.banksystem.infrastructure.exception.ex;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
