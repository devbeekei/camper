package com.ss.camper.user.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.ss.camper.user.UserMock.initClientUser;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Test
    public void 회원_정보_수정() {
        final String nickname = "김캠퍼2";
        final String phone = "01022222222";

        final ClientUser clientUser = initClientUser(1L);
        clientUser.updateInfo(nickname, phone);

        assertThat(clientUser.getNickname()).isEqualTo(nickname);
        assertThat(clientUser.getPhone()).isEqualTo(phone);
    }

    @Test
    public void 회원_탈퇴() {
        final ClientUser clientUser = initClientUser(1L);
        clientUser.withdraw();

        assertThat(clientUser.isWithdrawal()).isTrue();
    }

    @Test
    public void 약관_동의() {
        final ClientUser clientUser = initClientUser(1L);
        clientUser.agreeTerms(TermsType.USE, true);
        clientUser.agreeTerms(TermsType.PRIVACY_POLICY, true);

        assertThat(clientUser.getAgreeTermsHistories().size()).isEqualTo(2);
        assertThat(clientUser.getUseAgreeTerms().isAgree()).isTrue();
        assertThat(clientUser.getPrivacyPolicyAgreeTerms().isAgree()).isTrue();
    }

}
