package com.ss.camper.oauth2.dto;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("response"));
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getBirthday() {
        return attributes.get("birthyear") + "-" + attributes.get("birthday");
    }

    @Override
    public String getPhone() {
        return "";
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("profile_image");
    }

}