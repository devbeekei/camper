package com.ss.camper.oauth2.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "security.oauth2")
public class OAuth2Properties {

    private final Token token = new Token();
    private final Uris uris = new Uris();

    @Getter
    @Setter
    public static class Token {
        private String tokenSecret;
        private long authTokenExpirationTime;
        private long findPasswordTokenExpirationTime;
    }

    @Getter
    public static class Uris {
        @Setter
        private String authorizedFailureRedirectUri;
        private List<String> authorizedRedirectUri = new ArrayList<>();
        public Uris authorizedRedirectUris(List<String> authorizedRedirectUri) {
            this.authorizedRedirectUri = authorizedRedirectUri;
            return this;
        }
    }

}
