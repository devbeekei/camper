package com.ss.camper.user.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class NotFoundUserException extends ConflictException {
    public NotFoundUserException() {
        super(ApiResponseType.NOT_FOUND_USER);
    }
}
