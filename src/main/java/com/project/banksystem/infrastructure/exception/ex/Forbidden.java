package com.project.banksystem.infrastructure.exception.ex;

public class Forbidden extends RuntimeException {
    public Forbidden(String message) {
        super(message);
    }
}
