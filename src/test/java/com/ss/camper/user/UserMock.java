package com.ss.camper.user;

import com.ss.camper.uploadFile.domain.FileType;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import com.ss.camper.user.application.dto.AgreeTermsHistoryDTO;
import com.ss.camper.user.application.dto.UserInfoDTO;
import com.ss.camper.user.domain.BusinessUser;
import com.ss.camper.user.domain.ClientUser;
import com.ss.camper.user.domain.UserProfileImage;
import com.ss.camper.user.domain.UserType;

import java.util.Date;

public class UserMock {

    public static final String EMAIL = "camper@gmail.com";
    public static final String PASSWORD = "1234";
    public static final String PASSWORD_HASH = "$2a$10$Jg9o67Akrg4Oimr4OHQZh.XtUCNnki39fxvUo6dq.y9frZR1yAnN.";
    public static final String NICKNAME = "김캠퍼";
    public static final String PHONE = "01012345678";
    public static final String PROFILE_ORIGIN_FILE_NAME = "originFileName.jpg";
    public static final String PROFILE_UPDATE_FILE_NAME = "uploadFileName.jpg";
    public static final String PROFILE_FULL_PATH = "https://.../uploadFileName.jpg";
    public static final String PROFILE_PATH = ".../uploadFileName.jpg";
    public static final long PROFILE_SIZE = 23514;
    public static final String PROFILE_EXT = "JPG";

    public static UserInfoDTO initUserInfoDTO(Long id, UserType userType) {
        return UserInfoDTO.builder()
                .id(id)
                .email(EMAIL)
                .userType(userType)
                .nickname(NICKNAME)
                .phone(PHONE)
                .withdrawal(false)
                .created(new Date())
                .useAgreeTerms(AgreeTermsHistoryDTO.builder()
                        .id(1L)
                        .agree(true)
                        .created(new Date()).build())
                .privacyPolicyAgreeTerms(AgreeTermsHistoryDTO.builder()
                        .id(2L)
                        .agree(true)
                        .created(new Date()).build())
                .profileImage(UploadFileDTO.builder()
                        .id(1L)
                        .originName(PROFILE_ORIGIN_FILE_NAME)
                        .uploadName(PROFILE_UPDATE_FILE_NAME)
                        .fullPath(PROFILE_FULL_PATH)
                        .path(PROFILE_PATH)
                        .size(PROFILE_SIZE)
                        .ext(PROFILE_EXT)
                        .build())
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
                .profileImage(UserProfileImage.builder()
                        .id(1L)
                        .fileType(FileType.USER_PROFILE)
                        .originName(PROFILE_ORIGIN_FILE_NAME)
                        .uploadName(PROFILE_UPDATE_FILE_NAME)
                        .fullPath(PROFILE_FULL_PATH)
                        .path(PROFILE_PATH)
                        .size(PROFILE_SIZE)
                        .ext(PROFILE_EXT)
                        .build())
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
                .profileImage(UserProfileImage.builder()
                        .id(1L)
                        .fileType(FileType.USER_PROFILE)
                        .originName(PROFILE_ORIGIN_FILE_NAME)
                        .uploadName(PROFILE_UPDATE_FILE_NAME)
                        .fullPath(PROFILE_FULL_PATH)
                        .path(PROFILE_PATH)
                        .size(PROFILE_SIZE)
                        .ext(PROFILE_EXT)
                        .build())
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
                .profileImage(UserProfileImage.builder()
                        .id(1L)
                        .fileType(FileType.USER_PROFILE)
                        .originName(PROFILE_ORIGIN_FILE_NAME)
                        .uploadName(PROFILE_UPDATE_FILE_NAME)
                        .fullPath(PROFILE_FULL_PATH)
                        .path(PROFILE_PATH)
                        .size(PROFILE_SIZE)
                        .ext(PROFILE_EXT)
                        .build())
                .build();
    }
}
