package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;
import org.springframework.security.core.AuthenticationException;

public class NotValidTokenException extends AuthenticationException {
    public NotValidTokenException() {
        super(ApiResponseType.NOT_VALID_TOKEN.getMessage());
    }
}
