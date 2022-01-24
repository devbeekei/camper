package com.ss.camper.user.application;

import com.ss.camper.common.payload.ApiResponseType;
import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.user.application.exception.AlreadySignUpEmailException;
import com.ss.camper.user.application.exception.NotFoundUserException;
import com.ss.camper.user.application.exception.NotMatchedPasswordException;
import com.ss.camper.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ClientUserRepository clientUserRepository;
    private final BusinessUserRepository businessUserRepository;

    private void signUpValidate(final UserDTO userDTO, final String password, final String passwordCheck) {
        // 비밀번호 일치 확인
        if (!password.equals(passwordCheck)) throw new NotMatchedPasswordException();
        // 이메일 중복 확인
        final String email = userDTO.getEmail();
        final long userCount = userRepository.countByEmail(email);
        if (userCount > 0) throw new AlreadySignUpEmailException();
    }

    @Transactional
    public UserDTO signUpClientUser(final UserDTO userDTO, final String password, final String passwordCheck) {
        this.signUpValidate(userDTO, password, passwordCheck);
        final ClientUser clientUser = clientUserRepository.save(ClientUser.builder()
            .email(userDTO.getEmail())
            .password(passwordEncoder.encode(password))
            .nickname(userDTO.getNickname())
            .phone(userDTO.getPhone())
            .build());
        return modelMapper.map(clientUser, UserDTO.class);
    }

    @Transactional
    public UserDTO signUpBusinessUser(final UserDTO userDTO, final String password, final String passwordCheck) {
        this.signUpValidate(userDTO, password, passwordCheck);
        final BusinessUser businessUser = businessUserRepository.save(BusinessUser.builder()
            .email(userDTO.getEmail())
            .password(passwordEncoder.encode(password))
            .nickname(userDTO.getNickname())
            .phone(userDTO.getPhone())
            .build());
        return modelMapper.map(businessUser, UserDTO.class);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserInfo(final long userId) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public UserDTO updateUserInfo(final long userId, final UserDTO userDTO) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        user.updateInfo(userDTO.getNickname(), userDTO.getPhone());
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public void withdrawUser(long userId) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        user.withdraw();
    }

}
