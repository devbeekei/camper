package com.ss.camper.auth.application;

import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.user.domain.User;
import com.ss.camper.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public UserDTO signIn(final String email, final String password) throws AuthenticationException {
        final Optional<User> loginUser = userRepository.findByEmail(email);
        if (loginUser.isEmpty()) return null;
        if (!passwordEncoder.matches(password, loginUser.get().getPassword())) return null;
        return modelMapper.map(loginUser.get(), UserDTO.class);
    }

}
