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
    NOT_FOUND(HttpStatus.NOT_FOUND, 40401, "Not found(Resource not found, invalid url, including invalid RPCs)"),

    // BAD_REQUEST
    REQUEST_NOT_VALID(HttpStatus.BAD_REQUEST, 40001, "Request is Not valid"),
    REQUEST_METHOD_NOT_SUPPORT(HttpStatus.BAD_REQUEST, 40002, "Method Not supported"),

    // UNAUTHORIZED
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 40101, "Unauthorized"),
    EMPTY_SOCIAL_EMAIL(HttpStatus.UNAUTHORIZED, 40102, "Empty Social email"),
    UNSUPPORTED_REDIRECT_URI(HttpStatus.UNAUTHORIZED, 40103, "Unsupported Redirect URI"),
    UNSUPPORTED_SOCIAL_TYPE(HttpStatus.UNAUTHORIZED, 40104, "Unsupported Social type"),
    UNSUPPORTED_USER_TYPE(HttpStatus.UNAUTHORIZED, 40105, "Unsupported User type"),
    ALREADY_SIGN_UP_OTHER_PROVIDER(HttpStatus.UNAUTHORIZED, 40106, "Already Sign up Other provider"),

    // FORBIDDEN
    NOT_VALID_TOKEN(HttpStatus.FORBIDDEN, 40301, "Not valid Token"),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, 40302, "Expired Token"),
    UNSUPPORTED_TOKEN(HttpStatus.FORBIDDEN, 40303, "Unsupported Token"),

    // CONFLICT
    NOT_FOUND_USER(HttpStatus.CONFLICT, 40901, "Not found user"),
    NOT_FOUND_BLOG(HttpStatus.CONFLICT, 40902, "Not found blog"),
    ALREADY_BLOG_REGISTERED(HttpStatus.CONFLICT, 40903, "Already blog registered"),
    NOT_FOUND_STORE(HttpStatus.CONFLICT, 40904, "Not found store"),
    NOT_SUPPLY_STORE_TYPE(HttpStatus.CONFLICT, 40905, "Not supply Store type");

    private final HttpStatus status;
    private final int code;
    private final String message;
}