package com.ss.camper.oauth2.dto;

import com.ss.camper.oauth2.exception.UnsupportedSocialTypeException;
import com.ss.camper.user.domain.SocialProvider;

import java.util.Map;


public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        switch (SocialProvider.valueOf(registrationId)) {
            case GOOGLE:
                return new GoogleOAuth2UserInfo(attributes);
            case KAKAO:
                return new KakaoOAuth2UserInfo(attributes);
            case NAVER:
                return new NaverOAuth2UserInfo(attributes);
            default:
                throw new UnsupportedSocialTypeException();
        }
    }

}