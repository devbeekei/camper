package com.ss.camper.auth.ui.payload;

import com.ss.camper.oauth2.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignInPayload {

    @ToString
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @Email
        @NotBlank
        private String email;
        @NotBlank
        private String password;
    }

    @ToString
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private UserDTO user;
        private String token;
    }

}
