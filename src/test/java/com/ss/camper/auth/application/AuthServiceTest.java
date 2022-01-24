package com.ss.camper.auth.application;

import com.ss.camper.auth.application.exception.NotMatchedPasswordException;
import com.ss.camper.auth.application.exception.NotSignedUpEmailException;
import com.ss.camper.auth.application.exception.SingInWithdrawUserException;
import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.user.domain.User;
import com.ss.camper.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.ss.camper.user.UserMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void 이메일_로그인() {
        final User user = initClientUser(1L);
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        final UserDTO result = authService.signIn(EMAIL, PASSWORD);

        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getUserType()).isEqualTo(user.getUserType());
        assertThat(result.getEmail()).isEqualTo(EMAIL);
        assertThat(result.getNickname()).isEqualTo(user.getNickname());
        assertThat(result.getPhone()).isEqualTo(user.getPhone());
    }

    @Test
    void 이메일_로그인_가입되지_않은_회원() {
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        assertThrows(NotSignedUpEmailException.class, () -> authService.signIn(EMAIL, PASSWORD));
    }

    @Test
    void 이메일_로그인_비밀번호_불일치() {
        final User user = initClientUser(1L);
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);
        assertThrows(NotMatchedPasswordException.class, () -> authService.signIn(EMAIL, PASSWORD));
    }

    @Test
    void 이메일_로그인_탈퇴회원() {
        final User user = initWithdrawClientUser(1L);
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        assertThrows(SingInWithdrawUserException.class, () -> authService.signIn(EMAIL, PASSWORD));
    }

}