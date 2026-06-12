package com.project.banksystem.infrastructure.exception.ex;

public class Unauthorized extends RuntimeException {
    public Unauthorized(String message) {
        super(message);
    }
}
