package com.project.banksystem.infrastructure.exception.ex;

public class SystemMisusedException extends RuntimeException {
    public SystemMisusedException(String message) {
        super(message);
    }
}
