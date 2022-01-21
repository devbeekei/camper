package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;
import org.springframework.security.core.AuthenticationException;

public class AlreadySignUpOtherProviderException extends AuthenticationException {
    public AlreadySignUpOtherProviderException() {
        super(ApiResponseType.ALREADY_SIGN_UP_OTHER_PROVIDER.getMessage());
    }
}
