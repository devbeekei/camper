package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;

public class EmptySocialEmailException extends CustomAuthenticationException {
    public EmptySocialEmailException() {
        super(ApiResponseType.EMPTY_SOCIAL_EMAIL);
    }
}
