package com.spring.security.dev.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "User id is not found."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "")
    ;

    private HttpStatus status;
    private String message;
}
