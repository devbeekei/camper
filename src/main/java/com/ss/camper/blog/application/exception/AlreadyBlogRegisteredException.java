package com.ss.camper.blog.application.exception;

import com.ss.camper.common.exception.ConflictException;
import com.ss.camper.common.payload.ApiResponseType;

public class AlreadyBlogRegisteredException extends ConflictException {
    public AlreadyBlogRegisteredException() {
        super(ApiResponseType.ALREADY_BLOG_REGISTERED);
    }
}
