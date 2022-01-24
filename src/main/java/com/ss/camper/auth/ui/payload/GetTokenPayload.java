package com.ss.camper.auth.ui.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

public class GetTokenPayload {

    @ToString
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotBlank
        private String code;
    }

    @ToString
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String token;
    }

}
