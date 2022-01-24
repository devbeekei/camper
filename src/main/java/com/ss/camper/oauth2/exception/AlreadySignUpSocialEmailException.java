package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;

public class AlreadySignUpSocialEmailException extends CustomAuthenticationException {
    public AlreadySignUpSocialEmailException() {
        super(ApiResponseType.SOCIAL_AUTH_ALREADY_SIGNED_UP_EMAIL);
    }
}
