package com.ss.camper.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    ADMIN("관리자 회원"),
    CLIENT("사용자 회원"),
    BUSINESS("사업자 회원");

    private String name;

}
