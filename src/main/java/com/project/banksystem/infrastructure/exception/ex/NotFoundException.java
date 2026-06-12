package com.project.banksystem.infrastructure.exception.ex;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
