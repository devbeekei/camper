package com.ss.camper.blog.application.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class NotFoundBlogException extends ConflictException {
    public NotFoundBlogException() {
        super(ApiResponseType.NOT_FOUND_BLOG);
    }
}
