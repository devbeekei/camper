package com.ss.camper.oauth2.handler;

import com.ss.camper.oauth2.config.AuthProperties;
import com.ss.camper.oauth2.application.HttpCookieOAuth2AuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final AuthProperties AuthProperties;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws  IOException {
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        String targetUrl = AuthProperties.getUris().getAuthorizedFailureRedirectUri();
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("message", exception.getLocalizedMessage().replace("[", "").replace("]", "_"))
            .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}