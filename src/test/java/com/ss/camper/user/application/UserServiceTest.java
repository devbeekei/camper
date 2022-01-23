package com.ss.camper.user.application;

import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.user.application.exception.SignUpFailedException;
import com.ss.camper.user.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ss.camper.user.UserMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

        final UserDTO userDTO = initUserDTO(null, UserType.CLIENT);
        final String password = "1234";
        final String passwordCheck = "1234";
        UserDTO result = userService.signUpClientUser(userDTO, password, passwordCheck);

        assertThat(result.getId()).isEqualTo(clientUser.getId());
        assertThat(result.getUserType()).isEqualTo(userDTO.getUserType());
        assertThat(result.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(result.getNickname()).isEqualTo(userDTO.getNickname());
        assertThat(result.getPhone()).isEqualTo(userDTO.getPhone());
    }

    @Test
    void 사용자_회원_회원가입_비밀번호_확인_불일치() {
        final UserDTO userDTO = initUserDTO(null, UserType.CLIENT);
        final String password = "1234";
        final String passwordCheck = "12345";
        assertThrows(SignUpFailedException.class, () -> userService.signUpClientUser(userDTO, password, passwordCheck));
    }

    @Test
    void 사용자_회원_회원가입_이미_가입된_이메일() {
        given(userRepository.countByEmail(anyString())).willReturn(1L);

        final UserDTO userDTO = initUserDTO(null, UserType.CLIENT);
        final String password = "1234";
        final String passwordCheck = "1234";
        assertThrows(SignUpFailedException.class, () -> userService.signUpClientUser(userDTO, password, passwordCheck));
    }

    @Test
    void 사업자_회원_회원가입() {
        BusinessUser businessUser = initBusinessUser(1L);
        given(userRepository.countByEmail(anyString())).willReturn(0L);
        given(businessUserRepository.save(any(BusinessUser.class))).willReturn(businessUser);

        final UserDTO userDTO = initUserDTO(null, UserType.BUSINESS);
        final String password = "1234";
        final String passwordCheck = "1234";
        UserDTO result = userService.signUpBusinessUser(userDTO, password, passwordCheck);

        assertThat(result.getId()).isEqualTo(businessUser.getId());
        assertThat(result.getUserType()).isEqualTo(userDTO.getUserType());
        assertThat(result.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(result.getNickname()).isEqualTo(userDTO.getNickname());
        assertThat(result.getPhone()).isEqualTo(userDTO.getPhone());
    }

    @Test
    void 사업자_회원_회원가입_비밀번호_확인_불일치() {
        final UserDTO userDTO = initUserDTO(null, UserType.BUSINESS);
        final String password = "1234";
        final String passwordCheck = "12345";
        assertThrows(SignUpFailedException.class, () -> userService.signUpBusinessUser(userDTO, password, passwordCheck));
    }

    @Test
    void 사업자_회원_회원가입_이미_가입된_이메일() {
        given(userRepository.countByEmail(anyString())).willReturn(1L);

        final UserDTO userDTO = initUserDTO(null, UserType.BUSINESS);
        final String password = "1234";
        final String passwordCheck = "1234";
        assertThrows(SignUpFailedException.class, () -> userService.signUpBusinessUser(userDTO, password, passwordCheck));
    }

}