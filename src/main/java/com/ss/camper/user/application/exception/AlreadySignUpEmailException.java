package com.ss.camper.user.application.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class AlreadySignUpEmailException extends ConflictException {
    public AlreadySignUpEmailException() {
        super(ApiResponseType.ALREADY_SIGNED_UP_EMAIL);
    }
}
