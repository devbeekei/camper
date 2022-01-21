package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;
import org.springframework.security.core.AuthenticationException;

public class UnsupportedSocialTypeException extends AuthenticationException {
    public UnsupportedSocialTypeException() {
        super(ApiResponseType.UNSUPPORTED_SOCIAL_TYPE.getMessage());
    }
}
