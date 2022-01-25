package com.ss.camper.user;

import com.ss.camper.user.application.dto.AgreeTermsHistoryDTO;
import com.ss.camper.user.application.dto.UserInfoDTO;
import com.ss.camper.user.domain.BusinessUser;
import com.ss.camper.user.domain.ClientUser;
import com.ss.camper.user.domain.UserType;

import java.util.Date;

public class UserMock {

    public static final String EMAIL = "camper@gmail.com";
    public static final String PASSWORD = "1234";
    public static final String PASSWORD_HASH = "$2a$10$Jg9o67Akrg4Oimr4OHQZh.XtUCNnki39fxvUo6dq.y9frZR1yAnN.";
    public static final String NICKNAME = "김캠퍼";
    public static final String PHONE = "01012345678";

    public static UserInfoDTO initUserInfoDTO(Long id, UserType userType) {
        return UserInfoDTO.builder()
                .id(id)
                .email(EMAIL)
                .userType(userType)
                .nickname(NICKNAME)
                .phone(PHONE)
                .withdrawal(false)
                .dateRecordCreated(new Date())
                .useAgreeTerms(AgreeTermsHistoryDTO.builder()
                        .id(1L)
                        .agree(true)
                        .dateRecordCreated(new Date()).build())
                .privacyPolicyAgreeTerms(AgreeTermsHistoryDTO.builder()
                        .id(2L)
                        .agree(true)
                        .dateRecordCreated(new Date()).build())
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
