package com.ss.camper.store.application.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class NotFoundStoreException extends ConflictException {
    public NotFoundStoreException() {
        super(ApiResponseType.NOT_FOUND_CAMP_STORE);
    }
}