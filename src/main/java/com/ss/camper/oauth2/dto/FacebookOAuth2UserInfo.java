package com.ss.camper.oauth2.dto;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class FacebookOAuth2UserInfo extends OAuth2UserInfo {

    public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        log.info(attributes.toString());
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("name");
    }

    @Override
    public String getName() {
        return (String) attributes.get("email");
    }

    @Override
    public String getBirthday() {
        return "";
    }

    @Override
    public String getPhone() {
        return "";
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("imageUrl");
    }

}
