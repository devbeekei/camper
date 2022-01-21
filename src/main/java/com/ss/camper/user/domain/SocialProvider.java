package com.ss.camper.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialProvider {

    GOOGLE( "Google"),
    KAKAO( "Kakao"),
    NAVER( "Naver");

    private String name;

}

