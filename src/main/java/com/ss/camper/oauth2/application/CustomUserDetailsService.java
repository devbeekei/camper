package com.ss.camper.oauth2.application;

import com.ss.camper.oauth2.dto.UserPrincipal;
import com.ss.camper.user.domain.User;
import com.ss.camper.user.domain.UserRepository;
import com.ss.camper.user.application.exception.NotFoundUserException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserPrincipal loadUserByUsername(String email)
        throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(NotFoundUserException::new);
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserPrincipal loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        return UserPrincipal.create(user);
    }

}