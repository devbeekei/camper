package com.ss.camper.auth.application.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class NotSignedUpEmailException extends ConflictException {
    public NotSignedUpEmailException() {
        super(ApiResponseType.NOT_SIGNED_UP_EMAIL);
    }
}
