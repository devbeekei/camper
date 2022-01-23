package com.ss.camper.user.ui.payload;

import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreTagDTO;
import com.ss.camper.store.domain.Address;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

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

        public UserDTO convertUserDTO() {
            return UserDTO.builder()
                .email(email)
                .nickname(nickname)
                .phone(phone)
                .build();
        }

    }

}
