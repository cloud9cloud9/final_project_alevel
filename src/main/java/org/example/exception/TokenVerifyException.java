package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TokenVerifyException extends ResponseStatusException {

    private static final String DEFAULT_MESSAGE = "Token verify failed.";

    public TokenVerifyException() {
        super(HttpStatus.UNAUTHORIZED, DEFAULT_MESSAGE);
    }
}
