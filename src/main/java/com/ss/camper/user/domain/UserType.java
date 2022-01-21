package com.ss.camper.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    CLIENT(1, "사용자 회원"),
    BUSINESS(1, "사업자 회원");

    private int id;
    private String name;

}
