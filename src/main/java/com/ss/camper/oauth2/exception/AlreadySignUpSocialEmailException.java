package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;

public class AlreadySignUpSocialEmailException extends CustomAuthenticationException {
    public AlreadySignUpSocialEmailException() {
        super(ApiResponseType.ALREADY_SIGNED_UP_SOCIAL_EMAIL);
    }
}
