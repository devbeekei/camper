package com.ss.camper.user.application.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class SignUpFailedException extends ConflictException {
    public SignUpFailedException(ApiResponseType apiResponseType) {
        super(apiResponseType);
    }
}
