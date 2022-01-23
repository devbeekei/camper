package com.ss.camper.auth.application.exception;

import org.springframework.security.core.AuthenticationException;

public class SignInFailedException extends AuthenticationException {
    public SignInFailedException(String msg) {
        super(msg);
    }
}
