package com.ss.camper.user.ui.payload;

import com.ss.camper.user.application.dto.UserInfoDTO;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignUpPayload {

    @ToString
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        @NotBlank
        private String passwordCheck;

        @NotBlank
        private String nickname;

        private String phone;

        public UserInfoDTO convertUserInfoDTO() {
            return UserInfoDTO.builder()
                .email(email)
                .nickname(nickname)
                .phone(phone)
                .build();
        }

    }

}
