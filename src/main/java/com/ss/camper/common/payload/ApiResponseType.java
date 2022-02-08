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
    NOT_FOUND(HttpStatus.NOT_FOUND, 101, "Not found(Resource not found, invalid url, including invalid RPCs)"),

    // BAD_REQUEST
    REQUEST_NOT_VALID(HttpStatus.BAD_REQUEST, 101, "Request is Not valid"),
    REQUEST_METHOD_NOT_SUPPORT(HttpStatus.BAD_REQUEST, 102, "Method Not supported"),

    // UNAUTHORIZED
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 101, "Unauthorized"),

    // 소셜 인증 중 오류
    SOCIAL_AUTH_EMPTY_SOCIAL_EMAIL(null, 101, "Empty Social email"),
    SOCIAL_AUTH_UNSUPPORTED_REDIRECT_URI(null, 102, "Unsupported Redirect URI"),
    SOCIAL_AUTH_UNSUPPORTED_SOCIAL_TYPE(null, 103, "Unsupported Social type"),
    SOCIAL_AUTH_UNSUPPORTED_USER_TYPE(null, 104, "Unsupported User type"),
    SOCIAL_AUTH_ALREADY_SIGNED_UP_EMAIL(null, 105, "Already signed up Email"),
    SOCIAL_AUTH_WITHDRAW_USER(null, 106, "Withdraw user"),

    // FORBIDDEN
    NOT_VALID_TOKEN(HttpStatus.FORBIDDEN, 101, "Not valid Token"),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, 102, "Expired Token"),
    UNSUPPORTED_TOKEN(HttpStatus.FORBIDDEN, 103, "Unsupported Token"),

    // User 관련 CONFLICT
    NOT_SIGNED_UP_EMAIL(HttpStatus.CONFLICT, 101, "Not signed up Email"),
    ALREADY_SIGNED_UP_EMAIL(HttpStatus.CONFLICT, 102, "Already signed up Email"),
    NOT_MATCHED_PASSWORD(HttpStatus.CONFLICT, 103, "Not matched Password"),
    NOT_FOUND_USER(HttpStatus.CONFLICT, 104, "Not found user"),
    WITHDRAW_USER(HttpStatus.CONFLICT, 105, "Withdraw user"),

    // Blog 관련 CONFLICT
    NOT_FOUND_BLOG(HttpStatus.CONFLICT, 301, "Not found blog"),
    ALREADY_BLOG_REGISTERED(HttpStatus.CONFLICT, 302, "Already blog registered"),

    // Store 관련 CONFLICT
    NOT_FOUND_STORE(HttpStatus.CONFLICT, 401, "Not found store"),
    NOT_SUPPLY_STORE_TYPE(HttpStatus.CONFLICT, 402, "Not supply Store type"),

    // File 관련 CONFLICT
    FILE_UPLOAD_FAILED(HttpStatus.CONFLICT, 501, "File upload Failed");

    private final HttpStatus status;
    private final int code;
    private final String message;
}