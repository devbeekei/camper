package com.ss.camper.oauth2.application;

import com.ss.camper.common.util.CookieUtil;
import com.ss.camper.oauth2.config.OAuth2Properties;
import com.ss.camper.oauth2.dto.OAuth2UserInfo;
import com.ss.camper.oauth2.dto.OAuth2UserInfoFactory;
import com.ss.camper.oauth2.dto.UserPrincipal;
import com.ss.camper.oauth2.exception.AlreadySignUpOtherProviderException;
import com.ss.camper.oauth2.exception.EmptySocialEmailException;
import com.ss.camper.oauth2.exception.UnsupportedRedirectUriException;
import com.ss.camper.oauth2.exception.UnsupportedUserTypeException;
import com.ss.camper.user.businessUser.domain.BusinessUser;
import com.ss.camper.user.businessUser.domain.BusinessUserRepository;
import com.ss.camper.user.clientUser.domain.ClientUser;
import com.ss.camper.user.clientUser.domain.ClientUserRepository;
import com.ss.camper.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.net.URI;
import java.util.Optional;

import static com.ss.camper.oauth2.application.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static com.ss.camper.oauth2.application.HttpCookieOAuth2AuthorizationRequestRepository.USER_TYPE_COOKIE_NAME;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuth2Properties OAuth2Properties;
    private final UserRepository userRepository;
    private final ClientUserRepository clientUserRepository;
    private final BusinessUserRepository businessUserRepository;
    private final SocialAuthRepository socialAuthRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        // 소셜 회원 정보
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
            oAuth2UserRequest.getClientRegistration().getRegistrationId(),
            oAuth2User.getAttributes()
        );

        // 이메일이 비어있을땐 EmptySocialEmailException
        if (StringUtils.isBlank(oAuth2UserInfo.getEmail()))
            throw new EmptySocialEmailException();

        // redirect uri 확인
        Optional<Cookie> redirectUriCookie = CookieUtil.getCookie(REDIRECT_URI_PARAM_COOKIE_NAME);
        if (redirectUriCookie.isEmpty() || !this.isAuthorizedRedirectUri(redirectUriCookie.get().getValue()))
            throw new UnsupportedRedirectUriException();

        // 로그인 하려는 소셜
        SocialProvider loginProvider = SocialProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());

        // 이메일로 회원 조회
        Optional<User> user = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User loginUser;

        if (user.isPresent()) { // 회원이 존재할때
            // 다른 소셜로 가입했으면 AlreadySignUpOtherProviderException
            if (user.get().getSocialAuth() == null || !user.get().getSocialAuth().getProvider().equals(loginProvider))
                throw new AlreadySignUpOtherProviderException();
            loginUser = this.updateUser(user.get(), oAuth2UserInfo);
        } else { // 회원 가입
            // user_type 확인
            Optional<Cookie> userTypeCookie = CookieUtil.getCookie(USER_TYPE_COOKIE_NAME);
            if (userTypeCookie.isEmpty()) throw new UnsupportedUserTypeException();
            UserType userType = UserType.valueOf(userTypeCookie.get().getValue());

            loginUser = this.registerUser(oAuth2UserRequest, oAuth2UserInfo, userType);
        }

        return UserPrincipal.create(loginUser);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        return OAuth2Properties.getUris().getAuthorizedRedirectUri()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) && authorizedURI.getPort() == clientRedirectUri.getPort() && authorizedURI.getPath().equals(clientRedirectUri.getPath())) {
                        return true;
                    }
                    return false;
                });
    }

    private User registerUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo, UserType userType) {
        User user = null;
        switch (userType) {
            case CLIENT:
                user = clientUserRepository.save(ClientUser.builder()
                        .email(oAuth2UserInfo.getEmail())
                        .password("")
                        .nickname(oAuth2UserInfo.getName())
                        .phone(oAuth2UserInfo.getPhone())
                        .build());
                break;
            case BUSINESS:
                user = businessUserRepository.save(BusinessUser.builder()
                        .email(oAuth2UserInfo.getEmail())
                        .password("")
                        .nickname(oAuth2UserInfo.getName())
                        .phone(oAuth2UserInfo.getPhone())
                        .build());
                break;
            default:
                throw new UnsupportedUserTypeException();
        }
        socialAuthRepository.save(SocialAuth.builder()
                .user_id(user.getId())
                .providerId(oAuth2UserInfo.getId())
                .provider(SocialProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .email(oAuth2UserInfo.getEmail())
                .name(oAuth2UserInfo.getName())
                .profileImage(oAuth2UserInfo.getImageUrl())
                .birthday(oAuth2UserInfo.getBirthday())
                .build());
        return user;
    }

    private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        user.getSocialAuth().infoUpdate(
                oAuth2UserInfo.getName(),
                oAuth2UserInfo.getPhone(),
                oAuth2UserInfo.getBirthday(),
                oAuth2UserInfo.getImageUrl());
        return user;
    }

}