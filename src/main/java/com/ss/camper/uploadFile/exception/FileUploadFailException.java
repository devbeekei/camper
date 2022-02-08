package com.ss.camper.uploadFile.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class FileUploadFailException extends ConflictException {
    public FileUploadFailException() {
        super(ApiResponseType.FILE_UPLOAD_FAILED);
    }
}
