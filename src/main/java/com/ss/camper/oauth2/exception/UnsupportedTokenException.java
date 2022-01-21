package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;
import org.springframework.security.core.AuthenticationException;

public class UnsupportedTokenException extends AuthenticationException {
    public UnsupportedTokenException() {
        super(ApiResponseType.UNSUPPORTED_TOKEN.getMessage());
    }
}
