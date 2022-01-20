package com.ss.camper.common.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiResponseType {
    // SUCCESS
    SUCCESS(HttpStatus.OK, 200, "Success"),

    // NOT_FOUND
    NOT_FOUND(HttpStatus.NOT_FOUND, 100, "Not found(Resource not found, invalid url, including invalid RPCs)"),

    // BAD_REQUEST
    REQUEST_NOT_VALID(HttpStatus.BAD_REQUEST, 100, "Request is Not valid"),
    REQUEST_METHOD_NOT_SUPPORT(HttpStatus.BAD_REQUEST, 101, "Method Not supported"),

    // CONFLICT
    NOT_FOUND_USER(HttpStatus.CONFLICT, 100, "Not found user"),

    NOT_FOUND_BLOG(HttpStatus.CONFLICT, 200, "Not found blog"),
    ALREADY_BLOG_REGISTERED(HttpStatus.CONFLICT, 201, "Already blog registered"),

    NOT_FOUND_STORE(HttpStatus.CONFLICT, 300, "Not found store"),
    NOT_SUPPLY_STORE_TYPE(HttpStatus.CONFLICT, 301, "Not supply Store type");

    private final HttpStatus status;
    private final int code;
    private final String message;
}