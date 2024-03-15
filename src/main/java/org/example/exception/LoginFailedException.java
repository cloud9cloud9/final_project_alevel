package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class LoginFailedException extends ResponseStatusException {

    @Serial
    private static final long serialVersionUID = 11L;

    private static final String DEFAULT_MESSAGE = "Invalid login credentials provided";

    public LoginFailedException() {
        super(HttpStatus.UNAUTHORIZED, DEFAULT_MESSAGE);
    }
}
