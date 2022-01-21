package com.ss.camper.auth.application;

import com.ss.camper.auth.domain.AuthCode;
import com.ss.camper.auth.domain.AuthCodeRepository;
import com.ss.camper.common.util.AuthCodeUtil;
import com.ss.camper.common.util.JWTUtil;
import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.oauth2.dto.UserPrincipal;
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

    /**
     * 인증 코드 발급
     * 최종 작업자 : 신봉교
     * 최종 작업일 : 2021-11-15
     * @param userPrincipal     인증 정보
     * @param redirectUri       Redirect URI
     * @return                  발급된 인증 코드
     */
    @Transactional
    public String issueAuthCode(final UserPrincipal userPrincipal, final String redirectUri) {
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        // Auth Code 생성
        final String authCode = authCodeUtil.creatAuthCode(Long.parseLong(userPrincipal.getName()));
        // Token 생성
        final String token = JWTUtil.creatAuthToken(authentication);
        // Token 만료일자
        final Date expired = JWTUtil.getExpiredDate(token);

        authCodeRepository.save(AuthCode.builder()
                .authCode(authCode)
                .userId(Long.parseLong(userPrincipal.getName()))
                .token(token)
                .expired(expired)
                .returnUri(redirectUri)
                .build());

        return authCode;
    }
    public String issueAuthCode(UserDTO user) {
        return this.issueAuthCode(UserPrincipal.create(user), null);
    }

    /**
     * 인증 토큰 발급
     * 종 작업자 : 신봉교
     * 최종 작업일 : 2021-11-15
     * @param code      인증 코드
     * @return          발급된 인증 토큰
     */
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
