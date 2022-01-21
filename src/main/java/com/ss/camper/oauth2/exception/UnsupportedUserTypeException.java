package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;
import org.springframework.security.core.AuthenticationException;

public class UnsupportedUserTypeException extends AuthenticationException {
    public UnsupportedUserTypeException() {
        super(ApiResponseType.UNSUPPORTED_USER_TYPE.getMessage());
    }
}
