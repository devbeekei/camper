package com.ss.camper.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TermsType {

    USE("이용 약관"),
    PRIVACY_POLICY("개인정보 처리방침");

    private String name;
}
