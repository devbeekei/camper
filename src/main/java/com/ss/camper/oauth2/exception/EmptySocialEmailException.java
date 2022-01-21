package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;
import org.springframework.security.core.AuthenticationException;

public class EmptySocialEmailException extends AuthenticationException {
    public EmptySocialEmailException() {
        super(ApiResponseType.EMPTY_SOCIAL_EMAIL.getMessage());
    }
}
