package com.ss.camper.user.application;

import com.ss.camper.user.application.dto.UserInfoDTO;
import com.ss.camper.user.application.exception.AlreadySignUpEmailException;
import com.ss.camper.user.application.exception.NotFoundUserException;
import com.ss.camper.user.application.exception.NotMatchedPasswordException;
import com.ss.camper.user.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.ss.camper.user.UserMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Spy
    private ModelMapper modelMapper;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientUserRepository clientUserRepository;

    @Mock
    private BusinessUserRepository businessUserRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void 사용자_회원_회원가입() {
        ClientUser clientUser = initClientUser(1L);
        given(userRepository.countByEmail(anyString())).willReturn(0L);
        given(clientUserRepository.save(any(ClientUser.class))).willReturn(clientUser);

        final UserInfoDTO userInfoDTO = initUserInfoDTO(null, UserType.CLIENT);
        final String password = "1234";
        final String passwordCheck = "1234";
        UserInfoDTO result = userService.signUpClientUser(userInfoDTO, password, passwordCheck);

        assertThat(result.getId()).isEqualTo(clientUser.getId());
        assertThat(result.getUserType()).isEqualTo(userInfoDTO.getUserType());
        assertThat(result.getEmail()).isEqualTo(userInfoDTO.getEmail());
        assertThat(result.getNickname()).isEqualTo(userInfoDTO.getNickname());
        assertThat(result.getPhone()).isEqualTo(userInfoDTO.getPhone());
    }

    @Test
    void 사용자_회원_회원가입_비밀번호_확인_불일치() {
        final UserInfoDTO userInfoDTO = initUserInfoDTO(null, UserType.CLIENT);
        final String password = "1234";
        final String passwordCheck = "12345";
        assertThrows(NotMatchedPasswordException.class, () -> userService.signUpClientUser(userInfoDTO, password, passwordCheck));
    }

    @Test
    void 사용자_회원_회원가입_이미_가입된_이메일() {
        given(userRepository.countByEmail(anyString())).willReturn(1L);

        final UserInfoDTO userInfoDTO = initUserInfoDTO(null, UserType.CLIENT);
        final String password = "1234";
        final String passwordCheck = "1234";
        assertThrows(AlreadySignUpEmailException.class, () -> userService.signUpClientUser(userInfoDTO, password, passwordCheck));
    }

    @Test
    void 사업자_회원_회원가입() {
        BusinessUser businessUser = initBusinessUser(1L);
        given(userRepository.countByEmail(anyString())).willReturn(0L);
        given(businessUserRepository.save(any(BusinessUser.class))).willReturn(businessUser);

        final UserInfoDTO userInfoDTO = initUserInfoDTO(null, UserType.BUSINESS);
        final String password = "1234";
        final String passwordCheck = "1234";
        UserInfoDTO result = userService.signUpBusinessUser(userInfoDTO, password, passwordCheck);

        assertThat(result.getId()).isEqualTo(businessUser.getId());
        assertThat(result.getUserType()).isEqualTo(userInfoDTO.getUserType());
        assertThat(result.getEmail()).isEqualTo(userInfoDTO.getEmail());
        assertThat(result.getNickname()).isEqualTo(userInfoDTO.getNickname());
        assertThat(result.getPhone()).isEqualTo(userInfoDTO.getPhone());
    }

    @Test
    void 사업자_회원_회원가입_비밀번호_확인_불일치() {
        final UserInfoDTO userInfoDTO = initUserInfoDTO(null, UserType.BUSINESS);
        final String password = "1234";
        final String passwordCheck = "12345";
        assertThrows(NotMatchedPasswordException.class, () -> userService.signUpBusinessUser(userInfoDTO, password, passwordCheck));
    }

    @Test
    void 사업자_회원_회원가입_이미_가입된_이메일() {
        given(userRepository.countByEmail(anyString())).willReturn(1L);
        final UserInfoDTO userInfoDTO = initUserInfoDTO(null, UserType.BUSINESS);
        final String password = "1234";
        final String passwordCheck = "1234";
        assertThrows(AlreadySignUpEmailException.class, () -> userService.signUpBusinessUser(userInfoDTO, password, passwordCheck));
    }

    @Test
    void 회원_정보_조회() {
        final long userId = 1;
        final ClientUser clientUser = initClientUser(userId);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(clientUser));

        final UserInfoDTO result = userService.getUserInfo(userId);

        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getUserType()).isEqualTo(clientUser.getUserType());
        assertThat(result.getEmail()).isEqualTo(clientUser.getEmail());
        assertThat(result.getNickname()).isEqualTo(clientUser.getNickname());
        assertThat(result.getPhone()).isEqualTo(clientUser.getPhone());
    }

    @Test
    void 존재하지_않는_회원_정보_조회() {
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(NotFoundUserException.class, () -> userService.getUserInfo(anyLong()));
    }

    @Test
    void 회원_정보_수정() {
        final long userId = 1;
        final ClientUser clientUser = initClientUser(userId);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(clientUser));

        final UserInfoDTO userInfoDTO = UserInfoDTO.builder().nickname("김킴퍼2").phone("01022222222").build();
        final UserInfoDTO result = userService.updateUserInfo(userId, userInfoDTO);

        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getNickname()).isEqualTo(userInfoDTO.getNickname());
        assertThat(result.getPhone()).isEqualTo(userInfoDTO.getPhone());
    }

    @Test
    void 존재하지_않는_회원_정보_수정() {
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(NotFoundUserException.class, () -> userService.updateUserInfo(1L, any(UserInfoDTO.class)));
    }

    @Test
    void 회원_탈퇴() {
        final long userId = 1;
        final ClientUser clientUser = initClientUser(userId);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(clientUser));

        userService.withdrawUser(userId);

        assertThat(clientUser.isWithdrawal()).isTrue();
    }

    @Test
    void 존재하지_않는_회원_탈퇴() {
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        assertThrows(NotFoundUserException.class, () -> userService.withdrawUser(anyLong()));
    }

}