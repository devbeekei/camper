package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;

public class UnsupportedSocialTypeException extends CustomAuthenticationException {
    public UnsupportedSocialTypeException() {
        super(ApiResponseType.SOCIAL_AUTH_UNSUPPORTED_SOCIAL_TYPE);
    }
}
