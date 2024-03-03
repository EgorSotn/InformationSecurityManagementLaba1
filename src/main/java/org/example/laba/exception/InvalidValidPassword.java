package org.example.laba.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidValidPassword extends AuthenticationException {
    public InvalidValidPassword(String message) {
        super(message);
    }
}
