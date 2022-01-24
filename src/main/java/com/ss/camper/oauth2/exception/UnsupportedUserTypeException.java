package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;

public class UnsupportedUserTypeException extends CustomAuthenticationException {
    public UnsupportedUserTypeException() {
        super(ApiResponseType.SOCIAL_AUTH_UNSUPPORTED_USER_TYPE);
    }
}
