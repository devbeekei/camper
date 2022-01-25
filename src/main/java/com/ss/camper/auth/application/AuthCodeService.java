package com.ss.camper.auth.application;

import com.ss.camper.auth.domain.AuthCode;
import com.ss.camper.auth.domain.AuthCodeRepository;
import com.ss.camper.common.util.AuthCodeUtil;
import com.ss.camper.common.util.JWTUtil;
import com.ss.camper.oauth2.dto.UserPrincipal;
import com.ss.camper.user.application.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthCodeService {

    private final JWTUtil JWTUtil;
    private final AuthCodeUtil authCodeUtil;
    private final AuthCodeRepository authCodeRepository;

    @Transactional
    public String issueAuthCode(final UserPrincipal userPrincipal, final String redirectUri) {
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        // Auth Code 생성
        final String code = authCodeUtil.creatAuthCode(Long.parseLong(userPrincipal.getName()));
        // Token 생성
        final String token = JWTUtil.creatAuthToken(authentication);
        // Token 만료일자
        final Date expired = JWTUtil.getExpiredDate(token);

        final AuthCode authCode = authCodeRepository.save(AuthCode.builder()
                .authCode(code)
                .userId(Long.parseLong(userPrincipal.getName()))
                .token(token)
                .expired(expired)
                .returnUri(redirectUri)
                .build());

        return authCode.getAuthCode();
    }

    public String issueAuthCode(final UserInfoDTO user) {
        return this.issueAuthCode(UserPrincipal.create(user), null);
    }

    @Transactional
    public String issueAuthToken(final String code) {
        final AuthCode authCode = authCodeRepository.findFirstByAuthCodeOrderByIdDesc(code).orElse(null);
        if (authCode != null) {
            return authCode.issueToken();
        } else {
            return null;
        }
    }

}
