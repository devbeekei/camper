package com.ss.camper.user.application;

import com.ss.camper.user.application.exception.NotFoundUserException;
import com.ss.camper.user.domain.ClientUser;
import com.ss.camper.user.domain.TermsType;
import com.ss.camper.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.ss.camper.user.UserMock.initClientUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserAgreeTermsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserAgreeTermsService userAgreeTermsService;

    @Test
    void 약관_동의() {
        final long userId = 1;
        final ClientUser clientUser = initClientUser(userId);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(clientUser));

        final Map<TermsType, Boolean> terms = new HashMap<>(){{ put(TermsType.USE, true); put(TermsType.PRIVACY_POLICY, true); }};
        userAgreeTermsService.agreeTerms(userId, terms);

        assertThat(clientUser.getAgreeTermsHistories().size()).isEqualTo(2);
        assertThat(clientUser.getUseAgreeTerms().isAgree()).isTrue();
        assertThat(clientUser.getPrivacyPolicyAgreeTerms().isAgree()).isTrue();
    }

    @Test
    void 존재하지_않는_회원_약관_동의() {
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(NotFoundUserException.class, () -> userAgreeTermsService.agreeTerms(1L, anyMap()));
    }

}