package com.ss.camper.auth.application.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class SingInWithdrawUserException extends ConflictException {
    public SingInWithdrawUserException() {
        super(ApiResponseType.WITHDRAW_USER);
    }
}
