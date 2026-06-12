package com.project.banksystem.infrastructure.exception.handler;

import com.project.banksystem.infrastructure.exception.ex.*;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PessimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class Handler {

    private static final Logger log = LoggerFactory.getLogger(Handler.class);

    public ResponseEntity<Map<String,Object>> response(Throwable throwable,
                                                          HttpStatus httpStatus,
                                                          HttpServletRequest request) {
        Map<String, Object> errorList = new HashMap<>();

        errorList.put("Message", throwable.getLocalizedMessage());
        errorList.put("Time", Instant.now().toString());
        errorList.put("RequestURI", request.getRequestURI());
        errorList.put("RequestURL", request.getRequestURL());
        errorList.put("Path", request.getContextPath().toString());
        errorList.put("Error Type", throwable.getClass().getSimpleName());


        return new ResponseEntity<Map<String,Object>>(errorList, httpStatus);
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> businessExceptionHandler(BusinessException e,
                                                                        HttpServletRequest r) {
        return response(e, HttpStatus.CONFLICT, r);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFoundExceptionHandler(NotFoundException e,
                                                                        HttpServletRequest r) {
        return response(e, HttpStatus.NOT_FOUND, r);
    }

    @ExceptionHandler({Forbidden.class,
    SystemMisusedException.class})
    public ResponseEntity<Map<String,Object>> forbiddenHandler(Exception e,
                                                                        HttpServletRequest r) {
        return response(e, HttpStatus.FORBIDDEN, r);
    }


    @ExceptionHandler({OptimisticLockingFailureException.class,
                        OptimisticLockException.class})
    public ResponseEntity<Map<String, Object>> optimisticExceptionHandler(RuntimeException e,
                                                                        HttpServletRequest r) {
        return response(e, HttpStatus.CONFLICT, r);
    }

    @ExceptionHandler({PessimisticLockException.class, PessimisticLockingFailureException.class,
    DeadlockLoserDataAccessException.class})
    public ResponseEntity<Map<String, Object>> pessimisticExceptionHandler(RuntimeException e,
                                                                           HttpServletRequest r) {
        return response(e, HttpStatus.SERVICE_UNAVAILABLE, r);
    }

    @ExceptionHandler(InsufficientFundException.class)
    public ResponseEntity<Map<String, Object>> insufficientFundExceptionHandler(InsufficientFundException e,
                                                                                HttpServletRequest r) {
        return response(e, HttpStatus.UNPROCESSABLE_CONTENT, r);
    }

    @ExceptionHandler({Unauthorized.class, AuthenticationException.class})
    public ResponseEntity<Map<String, Object>> handleUnauthorized(Exception e,
                                                                            HttpServletRequest r) {
        return response(e, HttpStatus.UNAUTHORIZED, r);
    }

    @ExceptionHandler({MaximumLoginAttemptExceededException.class,
    RateLimitExceededException.class})
    public ResponseEntity<Map<String, Object>> handleTooManyRequest(Exception e,
                                                                            HttpServletRequest r) {
        return response(e, HttpStatus.TOO_MANY_REQUESTS, r);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e,
                                                               HttpServletRequest r) {
        log.error("Unhandled exception", e);
        return response(e, HttpStatus.INTERNAL_SERVER_ERROR, r);
    }



}
