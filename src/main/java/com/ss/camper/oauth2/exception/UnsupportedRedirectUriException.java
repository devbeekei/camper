package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;

public class UnsupportedRedirectUriException extends CustomAuthenticationException {
    public UnsupportedRedirectUriException() {
        super(ApiResponseType.SOCIAL_AUTH_UNSUPPORTED_REDIRECT_URI);
    }
}
