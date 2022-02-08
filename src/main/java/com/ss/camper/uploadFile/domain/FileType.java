package com.ss.camper.uploadFile.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {

    USER_PROFILE("회원 프로필"),
    STORE_PROFILE("매장 프로필"),
    STORE_PHOTO("매장 사진");

    private String name;

}
