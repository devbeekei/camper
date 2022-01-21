package com.ss.camper.oauth2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getId();
    public abstract String getEmail();
    public abstract String getName();
    public abstract String getBirthday();
    public abstract String getPhone();
    public abstract String getImageUrl();

}
