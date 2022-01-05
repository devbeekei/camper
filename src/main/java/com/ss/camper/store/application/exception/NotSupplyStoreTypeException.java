package com.ss.camper.store.application.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class NotSupplyStoreTypeException extends ConflictException {
    public NotSupplyStoreTypeException() {
        super(ApiResponseType.NOT_SUPPLY_STORE_TYPE);
    }
}
