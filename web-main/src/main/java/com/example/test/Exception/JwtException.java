package com.example.test.Exception;

import org.springframework.http.HttpStatus;

public class JwtException extends RuntimeException {
    private HttpStatus httpStatus;

    public JwtException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public JwtException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

