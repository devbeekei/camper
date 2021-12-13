package com.ss.camper.common.exception;

import com.ss.camper.common.payload.ApiResponseType;
import lombok.Getter;

public class ConflictException extends RuntimeException {
    @Getter
    private final ApiResponseType apiResponseType;
    public ConflictException(ApiResponseType apiResponseType) {
        this.apiResponseType = apiResponseType;
    }
}
