package com.ss.camper.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN("관리자 회원"),
    SUB_ADMIN("서브 관리자 회원"),
    USER("사용자 회원");

    private String name;
}
