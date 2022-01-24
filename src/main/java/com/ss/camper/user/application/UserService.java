package com.ss.camper.user.application;

import com.ss.camper.common.payload.ApiResponseType;
import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.user.application.exception.SignUpFailedException;
import com.ss.camper.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        if (!password.equals(passwordCheck)) throw new SignUpFailedException(ApiResponseType.NOT_MATCHED_PASSWORD);
        // 이메일 중복 확인
        final String email = userDTO.getEmail();
        final long userCount = userRepository.countByEmail(email);
        if (userCount > 0) throw new SignUpFailedException(ApiResponseType.ALREADY_SIGNED_UP_EMAIL);
    }

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

}
