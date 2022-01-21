package com.ss.camper.auth.ui.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

public class SignInPayload {

    @ToString
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }

}
