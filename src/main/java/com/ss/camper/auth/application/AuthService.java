package com.ss.camper.auth.application;

import com.ss.camper.auth.application.exception.SignInFailedException;
import com.ss.camper.common.payload.ApiResponseType;
import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.user.domain.User;
import com.ss.camper.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public UserDTO signIn(final String email, final String password) throws AuthenticationException {
        final User loginUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new SignInFailedException(ApiResponseType.NOT_SIGNED_UP_EMAIL));

        if (!passwordEncoder.matches(password, loginUser.getPassword()))
            throw new SignInFailedException(ApiResponseType.NOT_MATCHED_PASSWORD);

        if (loginUser.isWithdraw())
            throw new SignInFailedException(ApiResponseType.WITHDRAW_USER);

        return modelMapper.map(loginUser, UserDTO.class);
    }

}
