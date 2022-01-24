package com.ss.camper.user.application.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class NotMatchedPasswordException extends ConflictException {
    public NotMatchedPasswordException() {
        super(ApiResponseType.NOT_MATCHED_PASSWORD);
    }
}
