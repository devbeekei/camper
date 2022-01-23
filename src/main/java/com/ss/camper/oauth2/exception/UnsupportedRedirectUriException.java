package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;
import org.springframework.security.core.AuthenticationException;

public class UnsupportedRedirectUriException extends CustomAuthenticationException {
    public UnsupportedRedirectUriException() {
        super(ApiResponseType.UNSUPPORTED_REDIRECT_URI);
    }
}
