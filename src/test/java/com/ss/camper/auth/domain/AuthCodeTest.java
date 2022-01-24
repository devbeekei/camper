package com.ss.camper.auth.domain;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AuthCodeTest {

    @Test
    public void 토큰_발급_처리() {
        final String code = "1234567890_CODE";
        final String token = "1234567890_TOKEN";
        final AuthCode authCode = AuthCode.builder()
                .authCode(code)
                .token(token)
                .build();
        final String issueToken = authCode.issueToken();
        assertThat(issueToken).isEqualTo(token);
        assertThat(authCode.getAuthCode()).isEqualTo("[ISSUED]" + code);
    }

}
