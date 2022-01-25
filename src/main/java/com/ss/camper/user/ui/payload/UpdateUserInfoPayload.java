package com.ss.camper.user.ui.payload;

import com.ss.camper.user.application.dto.UserInfoDTO;
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

        public UserInfoDTO convertUserInfoDTO() {
            return UserInfoDTO.builder()
                    .nickname(nickname)
                    .phone(phone)
                    .build();
        }
    }

}
