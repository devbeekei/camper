package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;

public class EmptySocialEmailException extends CustomAuthenticationException {
    public EmptySocialEmailException() {
        super(ApiResponseType.SOCIAL_AUTH_EMPTY_SOCIAL_EMAIL);
    }
}
