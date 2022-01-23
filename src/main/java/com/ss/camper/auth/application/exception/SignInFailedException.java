package com.ss.camper.auth.application.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class SignInFailedException extends ConflictException {
    public SignInFailedException(ApiResponseType apiResponseType) {
        super(apiResponseType);
    }
}
