package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;
import org.springframework.security.core.AuthenticationException;

public class ExpiredTokenException extends CustomAuthenticationException {
    public ExpiredTokenException() {
        super(ApiResponseType.EXPIRED_TOKEN);
    }
}
