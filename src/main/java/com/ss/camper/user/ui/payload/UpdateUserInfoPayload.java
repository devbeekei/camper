package com.ss.camper.user.ui.payload;

import com.ss.camper.oauth2.dto.UserDTO;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class UpdateUserInfoPayload {

    @ToString
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotBlank
        private String nickname;
        private String phone;

        public UserDTO convertUserDTO() {
            return UserDTO.builder()
                    .nickname(nickname)
                    .phone(phone)
                    .build();
        }
    }

}
