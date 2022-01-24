package com.ss.camper.user;

import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.user.domain.BusinessUser;
import com.ss.camper.user.domain.ClientUser;
import com.ss.camper.user.domain.UserType;

public class UserMock {

    public static final String EMAIL = "camper@gmail.com";
    public static final String PASSWORD = "1234";
    public static final String PASSWORD_HASH = "$2a$10$Jg9o67Akrg4Oimr4OHQZh.XtUCNnki39fxvUo6dq.y9frZR1yAnN.";
    public static final String NICKNAME = "김캠퍼";
    public static final String PHONE = "01012345678";

    public static UserDTO initUserDTO(Long id, UserType userType) {
        return UserDTO.builder()
            .id(id)
            .email(EMAIL)
            .userType(userType)
            .nickname(NICKNAME)
            .phone(PHONE)
            .build();
    }

    public static ClientUser initClientUser(Long id) {
        return ClientUser.builder()
                .id(id)
                .email(EMAIL)
                .userType(UserType.CLIENT)
                .password(PASSWORD_HASH)
                .nickname(NICKNAME)
                .phone(PHONE)
                .withdrawal(false)
                .build();
    }

    public static ClientUser initWithdrawClientUser(Long id) {
        return ClientUser.builder()
                .id(id)
                .email(EMAIL)
                .userType(UserType.CLIENT)
                .password(PASSWORD_HASH)
                .nickname(NICKNAME)
                .phone(PHONE)
                .withdrawal(true)
                .build();
    }

    public static BusinessUser initBusinessUser(Long id) {
        return BusinessUser.builder()
                .id(id)
                .userType(UserType.BUSINESS)
                .email(EMAIL)
                .password(PASSWORD_HASH)
                .nickname(NICKNAME)
                .phone(PHONE)
                .withdrawal(false)
                .build();
    }
}
