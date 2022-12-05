package com.spring.security.dev.user.exception;

import com.spring.security.dev.user.dao.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(e.getMessage())); // -> RuntimeException Error Message를 넘겨주는 부분
    }

    @ExceptionHandler(HospitalReviewAppException.class)
    public ResponseEntity<?> hospitalReviewAppExceptionHandler(HospitalReviewAppException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().getMessage()));
    }
}
