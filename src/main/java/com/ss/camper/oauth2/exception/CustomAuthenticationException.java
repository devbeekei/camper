package com.ss.camper.oauth2.exception;

import com.ss.camper.common.payload.ApiResponseType;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
    @Getter
    private final ApiResponseType apiResponseType;
    public CustomAuthenticationException(ApiResponseType apiResponseType) {
        super(apiResponseType.getCode() + "::" + apiResponseType.getMessage());
        this.apiResponseType = apiResponseType;
    }
}
