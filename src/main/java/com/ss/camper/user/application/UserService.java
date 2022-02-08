package com.ss.camper.user.application;

import com.ss.camper.user.application.dto.UserInfoDTO;
import com.ss.camper.user.application.exception.AlreadySignUpEmailException;
import com.ss.camper.user.application.exception.NotFoundUserException;
import com.ss.camper.user.application.exception.NotMatchedPasswordException;
import com.ss.camper.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ClientUserRepository clientUserRepository;
    private final BusinessUserRepository businessUserRepository;

    private void signUpValidate(final UserInfoDTO userInfoDTO, final String password, final String passwordCheck) {
        // 비밀번호 일치 확인
        if (!password.equals(passwordCheck)) throw new NotMatchedPasswordException();
        // 이메일 중복 확인
        final String email = userInfoDTO.getEmail();
        final long userCount = userRepository.countByEmail(email);
        if (userCount > 0) throw new AlreadySignUpEmailException();
    }

    @Transactional
    public UserInfoDTO signUpClientUser(final UserInfoDTO userInfoDTO, final String password, final String passwordCheck) {
        this.signUpValidate(userInfoDTO, password, passwordCheck);
        final ClientUser clientUser = clientUserRepository.save(ClientUser.builder()
            .email(userInfoDTO.getEmail())
            .password(passwordEncoder.encode(password))
            .nickname(userInfoDTO.getNickname())
            .phone(userInfoDTO.getPhone())
            .build());
        return modelMapper.map(clientUser, UserInfoDTO.class);
    }

    @Transactional
    public UserInfoDTO signUpBusinessUser(final UserInfoDTO userInfoDTO, final String password, final String passwordCheck) {
        this.signUpValidate(userInfoDTO, password, passwordCheck);
        final BusinessUser businessUser = businessUserRepository.save(BusinessUser.builder()
            .email(userInfoDTO.getEmail())
            .password(passwordEncoder.encode(password))
            .nickname(userInfoDTO.getNickname())
            .phone(userInfoDTO.getPhone())
            .build());
        return modelMapper.map(businessUser, UserInfoDTO.class);
    }

    @Transactional(readOnly = true)
    public UserInfoDTO getUserInfo(final long userId) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        return modelMapper.map(user, UserInfoDTO.class);
    }

    @Transactional
    public UserInfoDTO updateUserInfo(final long userId, final UserInfoDTO userInfoDTO) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        user.updateInfo(userInfoDTO.getNickname(), userInfoDTO.getPhone());
        return modelMapper.map(user, UserInfoDTO.class);
    }

    @Transactional
    public void withdrawUser(final long userId) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        user.withdraw();
    }

}
