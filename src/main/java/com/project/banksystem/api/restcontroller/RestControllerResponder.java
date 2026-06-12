package com.project.banksystem.api.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class RestControllerResponder {

    public static final <T> ResponseEntity<T> responseWithHttpStatus(T body, HttpStatus httpStatus) {
        return new ResponseEntity<T>(body, httpStatus);
    }

    public static final <T> ResponseEntity<T> successResponse(T body) {
        return ResponseEntity.ok(body);
    }
}
