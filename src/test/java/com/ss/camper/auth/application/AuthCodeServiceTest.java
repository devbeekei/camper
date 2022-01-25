package com.ss.camper.auth.application;

import com.ss.camper.auth.domain.AuthCode;
import com.ss.camper.auth.domain.AuthCodeRepository;
import com.ss.camper.common.util.AuthCodeUtil;
import com.ss.camper.common.util.JWTUtil;
import com.ss.camper.oauth2.dto.UserPrincipal;
import com.ss.camper.user.application.dto.UserInfoDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthCodeServiceTest {

    @Mock
    private JWTUtil JWTUtil;

    @Mock
    private AuthCodeUtil authCodeUtil;

    @Mock
    private AuthCodeRepository authCodeRepository;

    @InjectMocks
    private AuthCodeService authCodeService;

    @Test
    void 인증_코드_발급() {
        final String code = "1234567890_CODE";
        final String token = "1234567890_TOKEN";
        final Date expiredDate = new Date();
        given(authCodeUtil.creatAuthCode(anyLong())).willReturn(code);
        given(JWTUtil.creatAuthToken(any(Authentication.class))).willReturn(token);
        given(JWTUtil.getExpiredDate(anyString())).willReturn(expiredDate);
        given(authCodeRepository.save(any(AuthCode.class))).willReturn(AuthCode.builder().authCode(code).token(token).build());

        final UserPrincipal userPrincipal = UserPrincipal.create(UserInfoDTO.builder().id(1L).email("camper@gmail.com").build());
        final String result = authCodeService.issueAuthCode(userPrincipal, null);

        assertThat(result).isEqualTo(code);
    }

    @Test
    void 인증_토큰_발급() {
        final String code = "1234567890_CODE";
        final String token = "1234567890_TOKEN";
        given(authCodeRepository.findFirstByAuthCodeOrderByIdDesc(anyString()))
                .willReturn(Optional.of(AuthCode.builder().authCode(code).token(token).build()));

        final String result = authCodeService.issueAuthToken(code);

        assertThat(result).isEqualTo(token);
    }

    @Test
    void 인증_토큰_발급_인증_코드_발급_기록_없음() {
        given(authCodeRepository.findFirstByAuthCodeOrderByIdDesc(anyString())).willReturn(Optional.empty());

        final String code = "1234567890_CODE";
        final String result = authCodeService.issueAuthToken(code);

        assertThat(result).isNull();
    }

}