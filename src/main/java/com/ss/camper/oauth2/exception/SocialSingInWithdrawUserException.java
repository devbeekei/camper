package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;

public class SocialSingInWithdrawUserException extends CustomAuthenticationException {
    public SocialSingInWithdrawUserException() {
        super(ApiResponseType.SOCIAL_AUTH_WITHDRAW_USER);
    }
}
