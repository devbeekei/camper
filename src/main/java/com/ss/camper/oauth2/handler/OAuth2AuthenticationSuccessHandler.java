package com.ss.camper.oauth2.handler;

import com.ss.camper.auth.application.AuthCodeService;
import com.ss.camper.common.util.CookieUtil;
import com.ss.camper.oauth2.application.HttpCookieOAuth2AuthorizationRequestRepository;
import com.ss.camper.oauth2.dto.UserPrincipal;
import com.ss.camper.oauth2.exception.UnsupportedRedirectUriException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.ss.camper.oauth2.application.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final AuthCodeService authCodeService;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("response has already been committed. unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie redirectUriCookie = CookieUtil.getCookie(REDIRECT_URI_PARAM_COOKIE_NAME)
                .orElseThrow(UnsupportedRedirectUriException::new);
        String redirectUri = redirectUriCookie.getValue();

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String authCode = authCodeService.issueAuthCode(userPrincipal, redirectUri);

        return UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("code", authCode)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

}