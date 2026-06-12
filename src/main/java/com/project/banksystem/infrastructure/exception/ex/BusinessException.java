package com.project.banksystem.infrastructure.exception.ex;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
